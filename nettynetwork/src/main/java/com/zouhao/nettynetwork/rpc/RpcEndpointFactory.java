package com.zouhao.nettynetwork.rpc;

import com.zouhao.nettynetwork.client.RpcClient;
import com.zouhao.nettynetwork.msg.Config;
import com.zouhao.nettynetwork.server.RpcServer;

public class RpcEndpointFactory {


    public RpcServer createServer(Config config){
        return new RpcServer() {
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

    public RpcClient createClient(Config config){
        return new RpcClient() {
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

}
