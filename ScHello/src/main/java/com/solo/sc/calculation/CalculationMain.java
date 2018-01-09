package com.solo.sc.calculation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalculationMain {
	public static double pointAx = 0.0;
	public static double pointAy = 0.0;
	public static double pointBx = 0.0;
	public static double pointBy = 0.0;
	public static double pointCx = 0.0;
	public static double pointCy = 0.0;
	public static double pointDx = 0.0;
	public static double pointDy = 0.0;
	public static double pointFx = 0.0;
	public static double pointFy = 0.0;
	
	public static Map<String, Double> A = new HashMap<String, Double>();
	public static Map<String, Double> B = new HashMap<String, Double>();
	public static Map<String, Double> C = new HashMap<String, Double>();
	public static Map<String, Double> D = new HashMap<String, Double>();
	public static Map<String, Double> F = new HashMap<String, Double>();
	public static double S_ABCD = 0.0;
	public static void init(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("已知坐标上有4个点A(ax,ay)、B(bx,by)、C(cx,cy)、D(dx,dy)能组成一个四边形，求第5个点F(fx,fy),是否在该四边形内。");
		System.out.println("请输入已知点A横坐标ax");
		pointAx = scanner.nextDouble();
		System.out.println("请输入已知点A横坐标ay");
		pointAy = scanner.nextDouble();
		A.put("x",pointAx);
		A.put("y",pointAy);
		
		System.out.println("请输入已知点B横坐标bx");
		pointBx = scanner.nextDouble();
		System.out.println("请输入已知点B横坐标by");
		pointBy = scanner.nextDouble();
		B.put("x",pointBx);
		B.put("y",pointBy);
		
		System.out.println("请输入已知点C横坐标cx");
		pointCx = scanner.nextDouble();
		System.out.println("请输入已知点C横坐标cy");
		pointCy = scanner.nextDouble();
		C.put("x",pointCx);
		C.put("y",pointCy);
		
		System.out.println("请输入已知点D横坐标dx");
		pointDx = scanner.nextDouble();
		System.out.println("请输入已知点D横坐标dy");
		pointDy = scanner.nextDouble();
		D.put("x",pointDx);
		D.put("y",pointDy);
		S_ABCD = isQuadrilateral();
		//判断是否成四边形
		if(S_ABCD <= 0){
			System.out.println("无法构成四边形ABCD，请重新输入点坐标");
			init();
		}
	}
	
	//判断是否成四边形,成则返回四边形面积，否则返回0
	public static double isQuadrilateral(){
		double sABC = calculationArea(A ,B ,C);
		double sCDA = calculationArea(C ,D ,A);
		//如果三角形ABC的面积和三角形CDA的面积任意一个为零,则不能围成四边形
		if(sABC == 0 || sCDA == 0){
			return 0;
		}
		//四边形的面积=三角形ABC的面积+三角形CDA的面积
		return sABC + sCDA;
	}
	
	//判断点F是否在四边形ABCD中.
	public static boolean isExist(){
		double sAFB = calculationArea(A ,B ,F);
		double sBFC = calculationArea(B ,C ,F);
		double sCFD = calculationArea(C ,D ,F);
		double sDFA = calculationArea(D ,A ,F);
		double S_sum = sAFB + sBFC + sCFD + sDFA;
		//四边形的面积=三角形AOB,BOC,COD,DOA之和,且后面四个三角形的面积皆不为零,则F点在四边形内。
		if(calculationTools(S_ABCD) == calculationTools(S_sum)){
			if(sAFB == 0 || sBFC == 0 || sCFD == 0 || sDFA == 0){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	//算三角形面积。设a,b,c为三角形三条边长,令s=(a+b+c)/2,则面积为：根下(s(s-a)(s-b)(s-c))
	public static double calculationArea(Map<String, Double> a ,Map<String, Double> b ,Map<String, Double> f){
		double line_a = calculationLineLength(a,b);
		double line_b = calculationLineLength(b,f);
		double line_c = calculationLineLength(f,a);
		//海伦公式
		double s = (line_a + line_b + line_c) / 2;
		double sABF = Math.sqrt(s * (s - line_a) * (s - line_b) * (s - line_c));
		return sABF;
	}
	
	//根据两点算边长
	public static double calculationLineLength(Map<String, Double> a ,Map<String, Double> b ){
		double x = Math.abs(Double.parseDouble(a.get("x").toString()) - Double.parseDouble(b.get("x").toString()));
		double y = Math.abs(Double.parseDouble(a.get("y").toString()) - Double.parseDouble(b.get("y").toString()));
		//勾股定理算边长，两条直角边的平方和等于斜边的平方
		double c = Math.sqrt(x * x + y * y);
		return c;
	}

	public static double calculationTools(double parm){
		BigDecimal bg = new BigDecimal(parm);
   //     double result = bg.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();//面积保留4位小数
        double result = bg.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();//面积四舍五入取整
        return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		init();
		System.out.println("可构成四边形ABCD。");
		System.out.println("请输入已知点F横坐标fx");
		pointFx = scanner.nextDouble();
		System.out.println("请输入已知点F横坐标fy");
		pointFy = scanner.nextDouble();
		F.put("x",pointFx);
		F.put("y",pointFy);
		System.out.println("---初始化数据完毕---");
		if(isExist()){
			System.out.println("OK---点F在四边形ABCD内");
		}else{
			System.out.println("NO---点F不在四边形ABCD内");
		}

	}

}
