<template>
    <div class="col-10 container-fluid" v-if="!!credentials">
        <b-jumbotron class="credentialscontainer border border-success bg-light">
            <b-row class="flex-row">
                <label for="name" class="col-2 control-label">name: </label>
                <b-form-input :readonly="!isEditable" type="text" class="col-9"
                              v-model="credentials.name" id="name"/>
            </b-row>
            <b-row class="flex-row">
                <label for="consumerKey" class="col-2 control-label">consumerKey: </label>
                <b-form-input :readonly="!isEditable" type="text" id="consumerKey"
                              v-model="credentials.consumerKey" class="col-9"/>
            </b-row>
            <b-row class="flex-row">
                <label for="consumerSecret" class="col-2 control-label">consumerSecret: </label>
                <b-form-input :readonly="!isEditable" type="text"
                              v-model="credentials.consumerSecret" id="consumerSecret" class="col-9"/>
            </b-row>
            <b-row class="flex-row">
                <label for="accessToken" class="col-2 control-label">accessToken: </label>
                <b-form-input :readonly="!isEditable" type="text"
                              v-model="credentials.accessToken" id="accessToken" class="col-9"/>
            </b-row>
            <b-row class="flex-row">
                <label for="consumerKey" class="col-2 control-label">accessTokenSecret: </label>
                <b-form-input :readonly="!isEditable" type="text"
                              v-model="credentials.accessTokenSecret" id="accessTokenSecret" class="col-9"/>
            </b-row>
            <b-row>
                <label for="proxyHost" class="col-2 control-label">proxyHost: </label>
                <b-form-input :readonly="!isEditable" type="text"
                              v-model="credentials.proxyHost" id="proxyHost" class="col-4" />
                <label for="proxyPort" class="col-1 control-label">proxyPort: </label>
                <b-form-input :readonly="!isEditable" type="number"
                              v-model="credentials.proxyPort" id="proxyPort" class="col-4"/>
            </b-row>
            <b-row class="justify-content-end">
                <b-button variant="primary" size="lg" class="editbutton float-right col-1" v-if="!isEditable"  @click="toggleEditable()">Edit</b-button>
                <b-button variant="success" size="lg" class="mybutton float-right col-1" v-if="isEditable" @click="toggleEditable(); saveCredentials()">Save</b-button>
                <b-button variant="secondary" size="lg" class="mybutton float-right col-1" v-if="isEditable" @click="toggleEditable()">Undo</b-button>
                <b-button variant="danger" size="lg" class="deletebutton float-right col-1" v-if="isEditable" @click="toggleEditable(); deleteCredentials()">Delete</b-button>
                <div class="col-1"></div>
            </b-row>
        </b-jumbotron>
    </div>
</template>

<script>
    import {AXIOS} from './http-common'
    import VueCtkDateTimePicker from 'vue-ctk-date-time-picker';
    import 'vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css';

    export default {
        name: 'credentials',
        props: {credentialFromParent: null},
        components: {
            VueCtkDateTimePicker
        },

        data() {
            return {
                errors: [],
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false,
                isEditable: false
            }
        },
        computed: {
            everythingIsReady() {
                return !!this.credential
            },
            credentials: {
                get: function () {
                    return this.credentialFromParent;
                },
                set: function (newValue) {
                    this.$emit('update:credentialFromParent', newValue)
                }
            }
        },
        methods: {
            // Fetches posts when the component is created.
            saveCredentials() {
                console.log("Mission to be sent:\n" + JSON.stringify(this.credentials))
                AXIOS.post(`/credentials/twitter`, this.credentials)
                    .then(response => {
                        // JSON responses are automatically parsed.
                        this.mission = response.data
                        console.log(response.data)
                        this.showResponse = true
                    })
                    .catch(e => {
                        this.errors.push(e)
                    })
            },
            deleteCredentials() {
                if(!!this.credentials)
                {
                    console.log("deleteCredentials called")
                    this.$emit('deleteCredentials', this.credentials)
                }
            },
            toggleEditable() {
                this.isEditable = !this.isEditable
            }
        }
    }

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .control-label {
        text-align: left;
        font-weight: bold;
    }
    .mybutton {
        margin-right: 20px;
    }

</style>
