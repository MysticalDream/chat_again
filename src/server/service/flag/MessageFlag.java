package server.service.flag;

public interface MessageFlag {
	int HEARTBEAT = 0x00;
	int LOGIN = 0x1;
	int REGISTER = 0x2;
	int PRIVATEMESSAGE = 0x3;
	int ADDFRIEND = 0x4;
	int DELETEFRIEND = 0x5;
	int GROUPMESSAGE = 0x6;
	int OFFLINE = 0x7;
	int GETUSERLIST=0x8;
}
