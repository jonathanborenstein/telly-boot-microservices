package com.telly.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;



@SpringBootApplication
public class BusApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusApplication.class, args);
	}

	@Bean
	public Queue queue1() {
		return new Queue("findbus");
	}
	
	@Bean
	public Queue queue2() {
		return new Queue("findbusdate");
	}
	
	@Bean
	public Queue queue3() {
		return new Queue("save");
	}

	@Autowired
	BusRepository repo;


	@RabbitListener(queues = "findbusdate")
	List<Bus> worker1(Bus message) {
	
		List<Bus> busList = new ArrayList<Bus>();

		busList = repo.findByDateAndLeaveFromAndGoingTo(message.getDate(), message.getLeaveFrom(), message.getGoingTo());

		return busList;
	}

	@RabbitListener(queues = "save")
	void worker2(Bus message) {

		repo.save(message);
		
	}

	@RabbitListener(queues = "findbus")
	Bus worker3(Long id) {
		Bus b = repo.findById(id);
		return b;
	}
}


interface BusRepository extends JpaRepository<Bus, Long>{

	List<Bus> findAll();

	Bus findById(Long id);

	List<Bus> findByDateAndLeaveFromAndGoingTo(Date date, String leaveFrom, String goingTo);

}
