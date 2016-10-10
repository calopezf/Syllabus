/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.syllabus.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import ec.edu.puce.syllabus.constantes.EnumEstado;
import ec.edu.puce.syllabus.constantes.EnumEstadoCivil;
import ec.edu.puce.syllabus.constantes.EnumRol;

/**
 *
 * @author juan
 */
@ManagedBean(name = "selectItemCtrl")
public class SelectItemCtrl extends BaseCtrl {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> estadoEnum;
	private List<SelectItem> rolEnum;
	private List<SelectItem> estadoCivil;
	private List<SelectItem> pais;
	private List<SelectItem> tipoIdentificacion;

	public List<SelectItem> getEstadoEnum() {
		if (estadoEnum == null) {
			estadoEnum = new ArrayList<SelectItem>();
			for (EnumEstado e : EnumEstado.values()) {
				estadoEnum.add(new SelectItem(e, getBundleEtiquetas(
						e.getEtiqueta(), null)));
			}
		}
		return estadoEnum;
	}

	public void setEstadoEnum(List<SelectItem> estadoEnum) {
		this.estadoEnum = estadoEnum;
	}

	public List<SelectItem> getRolEnum() {
		if (rolEnum == null) {
			rolEnum = new ArrayList<SelectItem>();
			for (EnumRol re : EnumRol.values()) {
				rolEnum.add(new SelectItem(re, re.toString()));
			}
		}
		return rolEnum;
	}

	public void setRolEnum(List<SelectItem> rolEnum) {
		this.rolEnum = rolEnum;
	}

	public List<SelectItem> getEstadoCivil() {
		if (estadoCivil == null) {
			estadoCivil = new ArrayList<SelectItem>();
			for (EnumEstadoCivil ec : EnumEstadoCivil.values()) {
				estadoCivil.add(new SelectItem(ec, ec.getEtiqueta()));
			}
		}
		return estadoCivil;
	}

	public void setEstadoCivil(List<SelectItem> estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

}
