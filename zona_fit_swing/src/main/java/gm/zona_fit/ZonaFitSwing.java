package gm.zona_fit;

import com.formdev.flatlaf.FlatDarculaLaf;
import gm.zona_fit.gui.ZonaFitForma;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ZonaFitSwing {

    public static void main(String[] args) {
        //configuramos el modo oscuroantes de que se lanze la aplicacion
        FlatDarculaLaf.setup();
        //Instancia de lña fabrica de spring
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(ZonaFitSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);

        //Crear un objeto de swing
        SwingUtilities.invokeLater(()-> {

          ZonaFitForma zonaFitForma =  contextoSpring.getBean(ZonaFitForma.class);

          zonaFitForma.setVisible(true);

        });

    }
}
