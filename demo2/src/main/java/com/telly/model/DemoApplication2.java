package com.telly.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;



@SpringBootApplication
public class DemoApplication2 {
	
	private static NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	@Autowired
	ReservationsRepo repo3;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication2.class, args);
	}

	@Bean
	public Queue myQueue5() {
		return new Queue("findreservations");
	}
	
	@Bean
	public Queue myQueue6() {
		return new Queue("delete");
	}

	@RabbitListener(queues = "delete")
	void worker1(Long id) {
		repo3.delete(id);
	}

	@RabbitListener(queues = "findreservations")
	List<Reservations> worker2(String email) {

		List<Reservations> reservationsList = new ArrayList<Reservations>();

		reservationsList = repo3.findByEmail(email);

		System.out.println(reservationsList);;
		return reservationsList;
	}

	@RabbitListener(queues = "reserve")
	void worker3(Reservations reservation) {
	
		create(reservation);
		System.out.println(repo3.findAll());
	}



	public static boolean create(Reservations reserve) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(reserve);

		return jdbc.update("insert into telly_reservations (bus_id, email) values (:busId, :email)", params) == 1;
	}
}



interface ReservationsRepo extends CrudRepository<Reservations, Long> {
	List<Reservations> findByEmail(String email);

}
