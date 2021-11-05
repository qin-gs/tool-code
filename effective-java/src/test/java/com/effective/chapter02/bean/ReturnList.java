package com.effective.chapter02.bean;

/**
 * 返回结果统一封装
 */
public class ReturnList {
	private boolean success;
	private String msg;
	private int code;
	private Object data;

	private ReturnList(boolean success, String msg, int code, Object data) {
		this.success = success;
		this.msg = msg;
		this.code = code;
		this.data = data;
	}

	public static ReturnList success(Object data) {
		return success("操作成功", data);
	}

	public static ReturnList success() {
		return success(null);
	}

	public static ReturnList success(String msg, Object data) {
		return ReturnList.builder()
				.success(true)
				.msg(msg)
				.code(200)
				.data(data)
				.build();
	}

	public static ReturnList error(int code, String msg) {
		return ReturnList.builder()
				.success(false)
				.msg(msg)
				.code(code)
				.build();
	}

	public static ReturnList error(String msg) {
		return error(500, msg);
	}

	public static ReturnList error() {
		return error("操作失败");
	}

	public static ResultListBuilder builder() {
		return new ResultListBuilder();
	}

	public boolean isSuccess() {
		return this.success;
	}

	public String getMsg() {
		return this.msg;
	}

	public int getCode() {
		return this.code;
	}

	public Object getData() {
		return this.data;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReturnList{" +
				"success=" + success +
				", msg='" + msg + '\'' +
				", code=" + code +
				", data=" + data +
				'}';
	}

	public static class ResultListBuilder {
		private boolean success;
		private String msg;
		private int code;
		private Object data;

		ResultListBuilder() {
		}

		public ResultListBuilder success(boolean success) {
			this.success = success;
			return this;
		}

		public ResultListBuilder msg(String msg) {
			this.msg = msg;
			return this;
		}

		public ResultListBuilder code(int code) {
			this.code = code;
			return this;
		}

		public ResultListBuilder data(Object data) {
			this.data = data;
			return this;
		}

		public ReturnList build() {
			return new ReturnList(success, msg, code, data);
		}

		@Override
		public String toString() {
			return "ResultListBuilder{" +
					"success=" + success +
					", msg='" + msg + '\'' +
					", code=" + code +
					", data=" + data +
					'}';
		}
	}
}
