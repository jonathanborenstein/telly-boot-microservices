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
public class ReservationApplication {
	
	private static NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	@Autowired
	ReservationsRepository repo;
	
	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}

	@Bean
	public Queue queue1() {
		return new Queue("findreservations");
	}
	
	@Bean
	public Queue queue2() {
		return new Queue("delete");
	}
	
	@Bean
	public Queue queue3() {
		return new Queue("reserve");
	}

	@RabbitListener(queues = "delete")
	void worker1(Long id) {
		repo.delete(id);
	}

	@RabbitListener(queues = "findreservations")
	List<Reservations> worker1(String email) {

		List<Reservations> reservationsList = new ArrayList<Reservations>();

		reservationsList = repo.findByEmail(email);

		return reservationsList;
	}

	@RabbitListener(queues = "reserve")
	void worker2(Reservations reservation) {
	
		create(reservation);
	}



	public static boolean create(Reservations reserve) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(reserve);

		return jdbc.update("insert into telly_reservations (bus_id, email) values (:busId, :email)", params) == 1;
	}
}



interface ReservationsRepository extends CrudRepository<Reservations, Long> {
	List<Reservations> findByEmail(String email);

}
