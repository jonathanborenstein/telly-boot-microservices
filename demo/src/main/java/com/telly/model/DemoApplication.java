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
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public Queue myQueue5() {
		return new Queue("findbus");
	}

	@Autowired
	RabbitRepo2 repo2;


	@RabbitListener(queues = "query-example-7")
	List<Bus> worker2(Bus message) {
	
		List<Bus> busList = new ArrayList<Bus>();

		busList = repo2.findByDateAndLeaveFromAndGoingTo(message.getDate(), message.getLeaveFrom(), message.getGoingTo());

		System.out.println(busList);
		return busList;
	}

	@RabbitListener(queues = "save")
	void worker3(Bus message) {

		repo2.save(message);
		
	}

	@RabbitListener(queues = "findbus")
	Bus worker4(Long id) {
		Bus b = repo2.findById(id);
		return b;
	}
}


interface RabbitRepo2 extends JpaRepository<Bus, Long>{

	List<Bus> findAll();

	Bus findById(Long id);

	List<Bus> findByDateAndLeaveFromAndGoingTo(Date date, String leaveFrom, String goingTo);

}
