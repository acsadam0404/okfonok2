package hu.okfonok;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;


@org.springframework.stereotype.Component
@Scope("singleton")
public class Config implements ApplicationContextAware {
	private static Config instance;

	@Value("${root}")
	private String root;


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		instance = applicationContext.getBean(Config.class);
	}


	public static Path getRoot() {
		return Paths.get(instance.root);
	}
}
