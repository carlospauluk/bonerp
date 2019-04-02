package com.bonsucesso.erp.ekt.ekt2espelhos;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.ekt.data.EKTDeptoFinder;
import com.bonsucesso.erp.ekt.data.EKTGradeFinder;
import com.bonsucesso.erp.ekt.data.EKTPlanoPagtoFinder;
import com.bonsucesso.erp.ekt.data.EKTSubDeptoFinder;
import com.bonsucesso.erp.ekt.model.EKTDepto;
import com.bonsucesso.erp.ekt.model.EKTGrade;
import com.bonsucesso.erp.ekt.model.EKTPlanoPagto;
import com.bonsucesso.erp.ekt.model.EKTSubDepto;
import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.vendas.data.PlanoPagtoFinder;
import com.bonsucesso.erp.vendas.model.PlanoPagto;


/**
 * Métodos que verificam se houve mudanças em certas tabelas (que não deveriam mudar).
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("checkEspelhos2BonERP")
public class CheckEspelhos2BonERP {

	protected static Logger logger = Logger.getLogger(CheckEspelhos2BonERP.class);

	public void check() {
		try {
			checkDeptos();
			checkSubdeptos();
			checkGrades();
			checkPlanosPagto();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkDeptos() throws Exception {
		List<Departamento> deptos = getDeptoFinder().findAll();
		List<EKTDepto> deptosEKT = getEktDeptoFinder().findAll();

		if (deptos == null || deptosEKT == null || deptos.size() != deptosEKT.size()) {
			throw new IllegalStateException("Deptos - problema com qtdes.");
		}

		for (Departamento depto : deptos) {
			EKTDepto ektDepto = getEktDeptoFinder().findByCodigo(depto.getCodigo().doubleValue());
			if (ektDepto.equalsToDepartamento(depto)) {
				throw new Exception("Depto modificao "
						+ "EKTDepto: " + ektDepto.toStringToView() + ", "
						+ "Depto: " + depto.toStringToView());
			}
		}

		logger.info("Deptos OK !!!");
	}

	public void checkSubdeptos() throws Exception {
		List<Subdepartamento> subdeptos = getSubdeptoFinder().findAll();
		// List<EKTSubDepto> subDeptosEKT = getEktSubDeptoFinder().findAll();

		// os subdeptos do BonERP tem que ter sempre 1 a mais, que é para os erros de importação
		//		if (subdeptos == null || subDeptosEKT == null || subdeptos.size() != subDeptosEKT.size() + 1) {
		//			throw new IllegalStateException("SubDeptos - problema com qtdes.");
		//		} *** NÃO VOU MAIS CONFERIR A QUANTIDADE, POIS MUDOU NO DECORRER DE 1 ANO ATRÁS PRA HOJE.

		for (Subdepartamento subDepto : subdeptos) {
			// pula o "<<<ERRO DE IMPORTAÇÃO>>>"
			if (subDepto.getCodigo().equals(0)) {
				continue;
			}
			EKTSubDepto ektSubDepto = null;
			try {
				ektSubDepto = getEktSubDeptoFinder().findByCodigo(subDepto.getCodigo().doubleValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ektSubDepto == null)
				continue;

			if (ektSubDepto.equalsToSubdepartamento(subDepto)) {
				throw new Exception("SubDepto modificao "
						+ "EKTSubDepto: " + ektSubDepto.toStringToView() + ", "
						+ "Subdepartamento: " + subDepto.toStringToView());
			}
		}

		logger.info("Subdeptos OK !!!");
	}

	public void checkGrades() throws Exception {
		List<Grade> grades = getGradeFinder().findAll();
		List<EKTGrade> gradesEKT = getEktGradeFinder().findAll();

		if (grades == null || gradesEKT == null || grades.size() != gradesEKT.size()) {
			throw new IllegalStateException("Grades - problema com qtdes.");
		}

		for (Grade grade : grades) {

			EKTGrade ektGrade = getEktGradeFinder().findByCodigo(grade.getCodigo().doubleValue());

			//			boolean ektDecimais = "S".equals(ektGrade.getDec());
			//
			//			if (!grade.getUnidadeProdutoPadrao().getDecimais().equals(ektDecimais)) {
			//				throw new Exception("Erro com grade (decimais)");
			//			}

			for (GradeTamanho gt : grade.getTamanhos()) {
				compararGrade(gt, ektGrade);
			}

		}

		logger.info("Grades OK !!!");

	}

	private void compararGrade(GradeTamanho gt, EKTGrade ektGrade) throws Exception {

		String ektTamanho = null;
		switch (gt.getOrdem()) {
			case 1:
				ektTamanho = ektGrade.getGra01();
				break;
			case 2:
				ektTamanho = ektGrade.getGra02();
				break;
			case 3:
				ektTamanho = ektGrade.getGra03();
				break;
			case 4:
				ektTamanho = ektGrade.getGra04();
				break;
			case 5:
				ektTamanho = ektGrade.getGra05();
				break;
			case 6:
				ektTamanho = ektGrade.getGra06();
				break;
			case 7:
				ektTamanho = ektGrade.getGra07();
				break;
			case 8:
				ektTamanho = ektGrade.getGra08();
				break;
			case 9:
				ektTamanho = ektGrade.getGra09();
				break;
			case 10:
				ektTamanho = ektGrade.getGra10();
				break;
			case 11:
				ektTamanho = ektGrade.getGra11();
				break;
			case 12:
				ektTamanho = ektGrade.getGra12();
				break;
			case 13:
				ektTamanho = ektGrade.getGra13();
				break;
			case 14:
				ektTamanho = ektGrade.getGra14();
				break;
			case 15:
				ektTamanho = ektGrade.getGra15();
				break;
		}

		if (!gt.getTamanho().equals(ektTamanho)) {
			throw new Exception("Erro em grade/tamanho");
		}

	}

	public void checkPlanosPagto() throws Exception {
		List<PlanoPagto> planosPagto = getPlanoPagtoFinder().findAll();
		List<EKTPlanoPagto> planosPagtoEKT = getEktPlanoPagtoFinder().findAll();

		if (planosPagto == null || planosPagtoEKT == null || planosPagto.size() < planosPagtoEKT.size()) {
			throw new IllegalStateException("PlanosPagto - problema com qtdes.");
		}

		for (PlanoPagto planoPagto : planosPagto) {
			EKTPlanoPagto ektPlanoPagto = getEktPlanoPagtoFinder().findByCodigo(planoPagto.getCodigo());
			if (ektPlanoPagto == null) {
				logger.info("EKTPlanoPagto não encontrado: " + planoPagto.toStringToView());
			} else {
				if (!ektPlanoPagto.equalsTo(planoPagto)) {
					throw new Exception("PlanoPagto modificado"
							+ "EKTPlanoPagto: " + ektPlanoPagto.toStringToView() + ", "
							+ "PlanoPagto: " + planoPagto.toStringToView());
				}
			}
		}

		logger.info("PlanosPagto OK !!!");
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		CheckEspelhos2BonERP t = (CheckEspelhos2BonERP) context.getBean("checkEspelhos2BonERP");
		t.check();
		System.exit(0);
	}

	@Autowired
	private DeptoFinder deptoFinder;

	@Autowired
	private EKTDeptoFinder ektDeptoFinder;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private EKTGradeFinder ektGradeFinder;

	@Autowired
	private EKTSubDeptoFinder ektSubDeptoFinder;

	@Autowired
	private PlanoPagtoFinder planoPagtoFinder;

	@Autowired
	private EKTPlanoPagtoFinder ektPlanoPagtoFinder;

	public DeptoFinder getDeptoFinder() {
		return deptoFinder;
	}

	public void setDeptoFinder(DeptoFinder deptoFinder) {
		this.deptoFinder = deptoFinder;
	}

	public EKTDeptoFinder getEktDeptoFinder() {
		return ektDeptoFinder;
	}

	public void setEktDeptoFinder(EKTDeptoFinder ektDeptoFinder) {
		this.ektDeptoFinder = ektDeptoFinder;
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public EKTGradeFinder getEktGradeFinder() {
		return ektGradeFinder;
	}

	public void setEktGradeFinder(EKTGradeFinder ektGradeFinder) {
		this.ektGradeFinder = ektGradeFinder;
	}

	public EKTSubDeptoFinder getEktSubDeptoFinder() {
		return ektSubDeptoFinder;
	}

	public void setEktSubDeptoFinder(EKTSubDeptoFinder ektSubDeptoFinder) {
		this.ektSubDeptoFinder = ektSubDeptoFinder;
	}

	public PlanoPagtoFinder getPlanoPagtoFinder() {
		return planoPagtoFinder;
	}

	public void setPlanoPagtoFinder(PlanoPagtoFinder planoPagtoFinder) {
		this.planoPagtoFinder = planoPagtoFinder;
	}

	public EKTPlanoPagtoFinder getEktPlanoPagtoFinder() {
		return ektPlanoPagtoFinder;
	}

	public void setEktPlanoPagtoFinder(EKTPlanoPagtoFinder ektPlanoPagtoFinder) {
		this.ektPlanoPagtoFinder = ektPlanoPagtoFinder;
	}

}
