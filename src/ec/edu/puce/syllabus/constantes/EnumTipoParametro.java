/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.syllabus.constantes;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author cristian
 */
public enum EnumTipoParametro {

	PLAN_ESTUDIOS, SEMESTRE, OCUPACION_PROFESOR, NIVEL_ALUMNO, AREA_MATERIA, CARRERA;

	public static List<EnumTipoParametro> getTipoParametroEnumList() {
		return Arrays.asList(EnumTipoParametro.values());
	}
}
