package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	//訊息:查詢成功,查詢失敗,新增失敗....
	private String message;
	T data;//payload實際資料
	//成功回應
	public static <T> ApiResponse<T> success(String message,T data){
		return new ApiResponse<T>(message, data);
	}
	//失敗回應
	public static <T> ApiResponse<T> fail(String message){
		return new ApiResponse<T>(message, null);
	}
}
