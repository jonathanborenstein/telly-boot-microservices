package com.telly.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.telly.model.Bus;
import com.telly.model.Reservations;

@Controller
public class ReservationsController {

	
	@Autowired
	RabbitTemplate template;

	@RequestMapping(value="/myreservations", method=RequestMethod.GET)
	ModelAndView myReservations(ModelAndView modelAndView, Principal principal){

		modelAndView.setViewName("app.myreservations");
		
		List<Reservations> reservations = new ArrayList<Reservations>();

		reservations = (List<Reservations>) template.convertSendAndReceive("h", "findreservations", principal.getName());
		
		HashMap<Long, Bus> myMap = new HashMap<Long, Bus>();

		Bus trip = new Bus();
		
		for(int i = 0; i < reservations.size(); i++){
			trip = (Bus) template.convertSendAndReceive("h", "findbus", reservations.get(i).getBusId());
			myMap.put(reservations.get(i).getId(), trip);
		}

		modelAndView.getModel().put("myMap", myMap);


		return modelAndView;
	}
	
	@RequestMapping(value="/myreservations", method=RequestMethod.POST)
	ModelAndView myReservations(ModelAndView modelAndView, Reservations reserve){


		template.convertAndSend("h", "delete", reserve.getId());
		modelAndView.setViewName("redirect:/myreservations");


		return modelAndView;
	}
}
