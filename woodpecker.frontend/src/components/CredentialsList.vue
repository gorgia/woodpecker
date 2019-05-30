<template>
    <b-container class="col-12 container-fluid">
        <b-row id="commandsrow">
            <b-col align="right" cols="10">
                <button class="" @click="createCredentials()">Insert New Credentials</button>
            </b-col>
        </b-row>
        <b-row id="credentials">
            <table id="credentialstable" class="table table-striped">
                <tr v-for="credential in credentialsList" :key="stringCredentialsId(credential)">
                    <credentials v-bind:credential-from-parent="credential" v-on:deleteCredentials="onDeleteCredentials()"></credentials>
                </tr>
            </table>
        </b-row>
    </b-container>
</template>

<script>
    import {AXIOS} from './http-common'
    import Credentials from "./Credentials";

    export default {
        name: 'twitter-credentials-list',
        components: {
            Credentials
        },
        data() {
            return {
                msg: 'HowTo call REST-Services:',
                response: {},
                errors: [],
                credentialsList: []
            }
        },
        mounted() {
            this.getAllCredentials()
        },
        computed: { },
        methods: {
            createCredentials() {
                this.credentialsList.unshift({})
            },
            // Fetches posts when the component is created.
            getAllCredentials() {
                AXIOS.get(`credentials/twitter/_all`)
                    .then(response => {
                        // JSON responses are automatically parsed.
                        this.response = response.data
                        this.credentialsList = this.response
                        console.log(response.data)
                    })
                    .catch(e => {
                        this.errors.push(e)
                    })
            },
            stringCredentialsId(credential) {
                if (!!credential && !!credential.id) return credential.id.toString()
                else return null
            },
            onDeleteCredentials(credentials){
                this.credentialsList.shift()
                console.log("called onDeleteCredentials")
                if(!!credentials)
                    AXIOS.delete(`/credentials/twitter`, credentials)
            }
        }
    }
</script>

<style scoped>
    #commandsrow {
        padding-bottom: 50px;
    }
</style>