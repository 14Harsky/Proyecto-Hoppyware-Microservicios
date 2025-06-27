package com.usuarios_hw.MS.Usuarios.HW;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableFeignClients
public class UsuariosHwApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariosHwApplication.class, args);
	}

	

}
