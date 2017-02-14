package br.com.procempa.modus.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Fábrica de Imagens para o software, provendo um ponto
 * único para a obtenção de ícones e imagens. 
 * @author petrillo
 *
 */
public class IconFactory {
	/**
	 * O caminho básico dos recursos de imagem
	 */
	static final String PATH = "resources/icons/";
	
	static IconFactory factory;

	/**
	 * Cache de imagens carregadas
	 */
	static Map<String,ImageIcon> iconsMap = new HashMap<String,ImageIcon>();
	
	private IconFactory() {
		//Singleton Pattern
	}
	
	public static IconFactory getInstance() {
		if (factory == null) {
			factory = new IconFactory();
		}
		
		return factory;
	}
	
	URL getURL(String resource) {
		//Deve-se utilizar o ClassLoader de uma instancia de objeto,
		//por causo no Java Web Start.
		return this.getClass().getClassLoader().getResource(PATH+resource);
	}
	
	/**
	 * Obtém a imagem do cache de imagens. Se ela não estiver
	 * carregada, carrega para o cache antes de retorná-la.
	 * @param iconName nome da imagem
	 * @return a imagem solicitada
	 */
	ImageIcon getImage(String iconName) {
		ImageIcon icon = iconsMap.get(iconName);
		if (icon == null) {
			icon = new ImageIcon(getURL(iconName));
			iconsMap.put(iconName, icon);
		}
		return icon;
	}

	public static ImageIcon createEdit() {
		return IconFactory.getInstance().getImage("portrait_16.gif");
	}

	public static ImageIcon createNew() {
		return IconFactory.getInstance().getImage("new_16.gif");
	}

	public static ImageIcon createDelete() {
		return IconFactory.getInstance().getImage("delete_16.gif");
	}

	public static ImageIcon createUser16() {
		return IconFactory.getInstance().getImage("usuario16.png");
	}

	public static ImageIcon createUser() {
		return IconFactory.getInstance().getImage("usuario32.png");
	}

	public static ImageIcon createClose() {
		return IconFactory.getInstance().getImage("exit_16.gif");
	}

	public static ImageIcon createSearch() {
		return IconFactory.getInstance().getImage("pesquisar.png");
	}

	public static ImageIcon createClear() {
		return IconFactory.getInstance().getImage("clear_16.gif");
	}

	public static ImageIcon createRefresh() {
		return IconFactory.getInstance().getImage("refresh_16.gif");
	}	
	
	public static ImageIcon createSave() {
		return IconFactory.getInstance().getImage("save_16.gif");
	}
	
	public static ImageIcon createPrint() {
		return IconFactory.getInstance().getImage("print_16.gif");
	}

	public static ImageIcon createManual() {
		return IconFactory.getInstance().getImage("manual_16.gif");
	}
	
	public static ImageIcon createDown16() {
		return IconFactory.getInstance().getImage("down_16.gif");
	}

	public static ImageIcon createAbout() {
		return IconFactory.getInstance().getImage("about_16.gif");
	}

	public static ImageIcon createSplash() {
		return IconFactory.getInstance().getImage("splash.png");
	}

	public static ImageIcon createModus() {
		return IconFactory.getInstance().getImage("modus16.png");
	}
	
	public static ImageIcon createModus32() {
		return IconFactory.getInstance().getImage("modus32.png");
	}

	public static ImageIcon createEncerraVisita() {
		return IconFactory.getInstance().getImage("salvarsair.png");
	}
    
    public static ImageIcon createEquipamento() {
		return IconFactory.getInstance().getImage("equipamentos.png");
    }

	public static ImageIcon createTelecentro() {
		return IconFactory.getInstance().getImage("telecentros32.png");
	}

	public static ImageIcon createTelecentro16() {
		return IconFactory.getInstance().getImage("telecentros16.png");
	}

    public static ImageIcon createVisita() {
		return IconFactory.getInstance().getImage("usuariosvermelho32.png");
    }

    public static ImageIcon createVisita16() {
		return IconFactory.getInstance().getImage("usuariosvermelho16.png");
    }
    
    public static ImageIcon createVisitaAtiva() {
		return IconFactory.getInstance().getImage("visitasativas32.png");
    }
    
    public static ImageIcon createStationMonitorTray() {
		return IconFactory.getInstance().getImage("stationmonitortray.png");
    }    

    public static ImageIcon createVisitaAtiva16() {
		return IconFactory.getInstance().getImage("visitasativas16.png");
    }

	public static ImageIcon createEquipamento16() {
		return IconFactory.getInstance().getImage("equipamentos16.png");
	}

	public static ImageIcon createWelcome() {
		return IconFactory.getInstance().getImage("welcome.jpg");
	}

	public static ImageIcon createBlock() {
		//return IconFactory.getInstance().getImage("block.jpg");
		return IconFactory.getInstance().getImage("bloqueio.jpg");
	}

	public static ImageIcon createUnblock() {
		return IconFactory.getInstance().getImage("desbloqueio.jpg");
	}
	
	public static ImageIcon createEquipamentoBlock16() {
		return IconFactory.getInstance().getImage("equipamentos_bloqueio16.png");
	}

	public static ImageIcon createEquipamentoBlock32() {
		return IconFactory.getInstance().getImage("equipamentos_bloqueio32.png");
	}

	public static ImageIcon createEquipamentoFree16() {
		return IconFactory.getInstance().getImage("equipamentos_libera16.png");
	}

	public static ImageIcon createEquipamentoFree32() {
		return IconFactory.getInstance().getImage("equipamentos_libera32.png");
	}

	public static ImageIcon createCurso32() {
		return IconFactory.getInstance().getImage("curso32.png");
	}

	public static ImageIcon createCurso16() {
		return IconFactory.getInstance().getImage("curso16.png");
	}

	public static ImageIcon createTurma16() {
		return IconFactory.getInstance().getImage("turma16.png");
	}

	public static ImageIcon createTurma32() {
		return IconFactory.getInstance().getImage("turma32.png");
	}
	
	public static ImageIcon createEncontro16() {
		return IconFactory.getInstance().getImage("encontro16.png");
	}

	public static ImageIcon createEncontro32() {
		return IconFactory.getInstance().getImage("encontro32.png");
	}
	
	public static ImageIcon createPresenca16() {
		return IconFactory.getInstance().getImage("presenca16.png");
	}

	public static ImageIcon createPresenca32() {
		return IconFactory.getInstance().getImage("presenca32.png");
	}
	
	public static ImageIcon createConteudo16() {
		return IconFactory.getInstance().getImage("conteudo16.png");
	}

	public static ImageIcon createConteudo32() {
		return IconFactory.getInstance().getImage("conteudo32.png");
	}
	
	public static ImageIcon createInscricao16() {
		return IconFactory.getInstance().getImage("inscritos16.png");
	}

	public static ImageIcon createInscricao32() {
		return IconFactory.getInstance().getImage("inscritos32.png");
	}
	
	public static ImageIcon createProcempaSmall() {
		return IconFactory.getInstance().getImage("procempa_small.gif");
	}
	
	public static ImageIcon createCertificado64() {
		return IconFactory.getInstance().getImage("certificado64.png");
	}
	
	public static ImageIcon createCertificado32() {
		return IconFactory.getInstance().getImage("certificado32.png");
	}
	
	public static ImageIcon createCertificado16() {
		return IconFactory.getInstance().getImage("certificado16.png");
	}
	
	public static ImageIcon createModusCertificado() {
		return IconFactory.getInstance().getImage("modusCertificado.png");
	}
}
