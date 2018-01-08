package com.test.caluclation;

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
	
	public static void init(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("已知坐标上有4个点A(ax,ay)、B(bx,by)、C(cx,cy)、D(dx,dy)能组成一个四边形，求第5个点F(fx,fy),是否在该四边形内。");
		System.out.println("请输入已知点A横坐标ax");
		pointAx = scanner.nextDouble();
		System.out.println("请输入已知点A横坐标ay");
		pointAy = scanner.nextDouble();
		
		System.out.println("请输入已知点B横坐标bx");
		pointBx = scanner.nextDouble();
		System.out.println("请输入已知点B横坐标by");
		pointBy = scanner.nextDouble();
		
		System.out.println("请输入已知点C横坐标cx");
		pointCx = scanner.nextDouble();
		System.out.println("请输入已知点C横坐标cy");
		pointCy = scanner.nextDouble();
		
		System.out.println("请输入已知点D横坐标dx");
		pointDx = scanner.nextDouble();
		System.out.println("请输入已知点D横坐标dy");
		pointDy = scanner.nextDouble();
		//判断是否成四边形
		//--------
		System.out.println("请输入已知点F横坐标fx");
		pointFx = scanner.nextDouble();
		System.out.println("请输入已知点F横坐标fy");
		pointFy = scanner.nextDouble();
	}
	
	//算面积
	public double calculationArea(){
		
		return 0;
		
	}
	
	//根据两点算边长
	public double calculationLineLength(){
		
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
		System.out.println("初始化数据完毕");

	}

}
