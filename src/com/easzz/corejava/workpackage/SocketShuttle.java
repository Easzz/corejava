package com.easzz.corejava.workpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketShuttle {
	public SocketShuttle(Socket sock) throws IOException {
		this.istr = sock.getInputStream();
		this.ostream = sock.getOutputStream();
	}

	public String recvData() throws Exception {
		byte[] len_b = recvByte(6);
		String sLen = new String(len_b, "GBK");
		int len = Integer.parseInt(sLen);
		if (len > 0) {
			byte[] data = recvByte(len);
			String sData = new String(data, "GBK");
			return sData;
		} else {
			return "";
		}
	}

	public void sendByte(byte[] data, int offset, int len) throws Exception {
		try {
			ostream.write(data, offset, len);
			ostream.flush();
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	private byte[] recvByte(int len) throws Exception {
		byte[] data = new byte[len];
		try {
			int len_recved = 0;
			while (len_recved < data.length) {
				int n = istr.read(data, len_recved, data.length - len_recved);
				len_recved += n;
			}
		} catch (IOException e) {
			System.out.println("recvByte error: " + e.getMessage());
			throw new Exception(e);
		}
		return data;
	}

	public void close() {
		if (ostream != null) {
			try {
				ostream.close();
			} catch (IOException e) {
			}
		}
		if (istr != null) {
			try {
				istr.close();
			} catch (IOException e) {
			}
		}
		ostream = null;
		istr = null;
	}

	protected void finalize() {
		//close();
		System.out.println("finalize....");
	}


	public static void main(String[] args) throws Exception {
		try {
			Socket clientsock;
			SocketShuttle shuttle;
			clientsock = new Socket("120.25.252.86", 8898);
			clientsock.setSoTimeout(60 * 1000);
			shuttle = new SocketShuttle(clientsock);
			//微信支付
			String data = "0004360103A0011000264285"+
			"9002002220159911191933130078151663743087"+
			"120160809161820"+
			"18000112341234千年野生葛根粉"+
			"117050311000袋";
/*//支付查询
//String data = "0001420203A001 1 0002
			64285 9002002220159911191933
			20160809161820 ";
//退款
//String data = "0002440303A001 1 0002
			64286 9002000220159911191927
			9002000220159911191926 201607291243201
			1 20160729124320 ";
//退款查询
//String data = "0002060403A001 1 0002
			64286 9002002220159911191927
			9002000220159911191926 20160729124320 ";
//撤销
//String data = "0001420503A001 1 0002
			64286 9002002220159911191933
			20160809161820 ";
//扫码
//String data = "0004360104A001 1 0002
			64285 9002002220159911191899
			130230552251421730 1 20160511153820
			1800011234 1234 千 年 野 生 葛 根 粉
			1170503 1 1000 袋
			";*/
			byte[] sendByte = data.getBytes();
			shuttle.sendByte(sendByte, 0, sendByte.length);
			String revData = shuttle.recvData();
			System.out.println("revData=" + revData);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	private OutputStream ostream = null;
	private InputStream istr = null;
}