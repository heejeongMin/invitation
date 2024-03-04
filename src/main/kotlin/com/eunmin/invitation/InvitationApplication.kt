package com.eunmin.invitation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class InvitationApplication

fun main(args: Array<String>) {
	runApplication<InvitationApplication>(*args)
}

@Configuration
open class ApplicationWebConfig : WebMvcConfigurer {
	override fun addViewControllers(registry: ViewControllerRegistry) {
		registry.addViewController("/").setViewName("index")
		registry.addViewController("/new").setViewName("new")
		registry.addViewController("/demo").setViewName("demo")
	}
}
