package com.frutacloud.socketsample;

/**
 * Created by Administrator on 2017/12/1.
 */

public class SocketConcat {

    public interface ISocketView {
        void showData(String str);

        void showLoading();

        void dismissLoading();

        String getMessage();

        void showToast(String msg);
    }

    public interface ISocketModel {

        void connect(String ip, int port, ClientCallBack<String> callBack);

        void disconnect();

        void send(String str);
    }

    public interface ISocketPersent {
        void connect();

        void disconnect();

        void send();
    }
}
