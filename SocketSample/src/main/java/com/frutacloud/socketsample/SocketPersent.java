package com.frutacloud.socketsample;

/**
 * Created by Administrator on 2017/12/1.
 */

public class SocketPersent implements SocketConcat.ISocketPersent {

    private SocketConcat.ISocketView iSocketView;
    private SocketConcat.ISocketModel iSocketModel;

    public SocketPersent(SocketConcat.ISocketView iSocketView) {
        this.iSocketView = iSocketView;
        iSocketModel = new SocketModel();
    }

    @Override
    public void connect() {
        iSocketView.showLoading();
        System.out.print("connect()");
        iSocketModel.connect("192.168.2.204", 1989, new ClientCallBack<String>() {
            @Override
            public void onSuccesful(String s) {
                iSocketView.dismissLoading();
                iSocketView.showData(s);
            }

            @Override
            public void onFaild(String st) {
                iSocketView.dismissLoading();
                iSocketView.showToast(st);
            }
        });
    }

    @Override
    public void disconnect() {
        iSocketModel.disconnect();
    }

    @Override
    public void send() {
        iSocketModel.send(iSocketView.getMessage());
    }
}
