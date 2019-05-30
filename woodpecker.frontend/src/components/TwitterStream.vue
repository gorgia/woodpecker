<template>
    <div>
        <div id="main-content" class="container">
            <div class="row">
                <div class="col-md-12">
                    <table id="conversation" class="table table-striped">
                        <thead>
                        <tr>
                            <th>Twitter Stream</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in received_messages" :key="item.id">
                            <tweet v-bind:tweetString="item"></tweet>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import SockJS from "sockjs-client";
    import Stomp from "webstomp-client";
    import Tweet from './Tweet'
    import {apiserveraddress} from "./http-common";

    export default {
        name: "twitter-stream",
        components: {
            Tweet
        },
        data() {
            return {
                received_messages: [],
                send_message: null,
                connected: false,
                socket: {},
                stompClient: {}
            };
        },
        methods: {
            connect() {
                let socketUrl = apiserveraddress + '/api/websocket'
                this.socket = new SockJS(socketUrl);
                this.stompClient = Stomp.over(this.socket);
                this.stompClient.connect(
                    {},
                    frame => {
                        this.connected = true;
                        console.log(frame);
                        this.stompClient.subscribe("/topic/tweets", tick => {
                            console.log("received\n" + tick);
                            this.received_messages.unshift(tick.body)
                            while(this.received_messages.length > 100){
                                let valuetonull = this.received_messages.pop()
                                valuetonull = undefined
                            }
                        });
                    },
                    error => {
                        console.log(error);
                        this.connected = false;
                    }
                );
            },
            disconnect() {
                if (this.stompClient) {
                    this.stompClient.disconnect();
                }
                this.connected = false;
            },
            tickleConnection() {
                this.connected ? this.disconnect() : this.connect();
            }
        },
        mounted() {
            this.connect();
        }
    };
</script>

<style scoped>

</style>