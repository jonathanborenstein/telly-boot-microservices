package com.telly.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.telly.model.Bus;
import com.telly.model.Reservations;

@Controller
public class BusController {

	@Autowired
	RabbitTemplate template;

	
	@RequestMapping(value="/createtrip", method=RequestMethod.GET)
	ModelAndView addStatus(ModelAndView modelAndView){

		modelAndView.setViewName("app.createtrip");

		Bus bus = new Bus();

		modelAndView.getModel().put("bus", bus);

		return modelAndView;
	}

	@RequestMapping(value="/createtrip", method=RequestMethod.POST)
	ModelAndView addStatus(ModelAndView modelAndView, @Valid Bus bus, BindingResult result){

		modelAndView.setViewName("app.createtrip");

		if(!result.hasErrors()){
			template.convertAndSend("h", "save", bus);
			modelAndView.getModel().put("bus", new Bus());
		}



		return modelAndView;
	}

	@RequestMapping(value="/results", method=RequestMethod.GET)
	ModelAndView addSearch(ModelAndView modelAndView){

		modelAndView.setViewName("app.results");

		Bus bus = new Bus();

		modelAndView.getModel().put("bus", bus);

		return modelAndView;
	}

	@RequestMapping(value="/results", method=RequestMethod.POST)
	ModelAndView results(ModelAndView modelAndView, @Valid @ModelAttribute ("bus")Bus bus, BindingResult result){

		modelAndView.setViewName("app.results");


		if(!result.hasErrors()){
	
			List<Bus> busList = new ArrayList<Bus>();
			busList = (List<Bus>) template.convertSendAndReceive("h", "findbusdate", bus);
			System.out.println(busList);
			modelAndView.getModel().put("results", busList);

		}


		return modelAndView;
	}

	@RequestMapping(value="/book", method=RequestMethod.POST)
	ModelAndView results(ModelAndView modelAndView, Reservations reserve, Bus bus, BindingResult result, Principal principal){


		if(!result.hasErrors()){
			reserve.setEmail(principal.getName());
			reserve.setBusId(bus.getId());
			template.convertAndSend("h", "reserve", reserve);
			modelAndView.setViewName("redirect:/myreservations");
		}

		return modelAndView;
	}


}
