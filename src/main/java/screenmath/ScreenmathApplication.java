package screenmath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import screenmath.principal.Principal;
import screenmath.repository.SerieRepository;


@SpringBootApplication
public class ScreenmathApplication implements CommandLineRunner  {

	@Autowired
    private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmathApplication.class, args);
	}

	/* Metodo para executar comando em vez de utilizar o meto main  */
	@Override
	public void run(String... args) throws Exception {

		
		Principal principal = new Principal(repository);
		principal.menuPrincipal();

	
	}

}
