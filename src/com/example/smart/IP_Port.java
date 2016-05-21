package com.example.smart;

public class IP_Port {
	private String _id;// 
	private String _IP;
	private String _Port;

	public String get_id() 
	{
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get_IP() {
		return _IP;
	}
	public void set_IP(String _IP) {
		this._IP = _IP;
	}
	public String get_Port() {
		return _Port;
	}
	public void set_Port(String _Port) {
		this._Port = _Port;
	}


	public IP_Port(String _id, String _IP, String _Port) {
		super();
		this._id = _id;
		this._IP = _IP;
		this._Port = _Port;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return _IP+":"+_Port;
	}
	

}