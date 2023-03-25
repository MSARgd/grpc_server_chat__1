package ma.enset.entities;

import io.grpc.Metadata;
import io.grpc.stub.StreamObserver;
import ma.enset.subs.Chat;

public class Client {
    public StreamObserver<Chat.ChatResponse> responseStreamObserver;
    private String addresIp;

    public void setResponseStreamObserver(StreamObserver<Chat.ChatResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }
    //    private Metadata metadata;

//    public Metadata getMetadata() {
//        return metadata;
//    }

//    public Client(String addresIp, Metadata metadata) {
//        this.addresIp = addresIp;
//        this.metadata = metadata;
//    }

//    public void setMetadata(Metadata metadata) {
//        this.metadata = metadata;
//    }

    public Client() {
    }

    public StreamObserver<Chat.ChatResponse> getResponseStreamObserver() {
        return responseStreamObserver;
    }

    public Client(StreamObserver<Chat.ChatResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }

    public Client(String addresIp) {
        this.addresIp = addresIp;
    }

    public String getAddresIp() {
        return addresIp;
    }

    public void setAddresIp(String addresIp) {
        this.addresIp = addresIp;
    }
}
