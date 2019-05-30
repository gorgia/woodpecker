<template>
    <b-container class="col-12 container-fluid">
        <b-row id="commandsrow">
            <b-col cols="10" id="createMissionButtonCol" class="float-left">
                <button class="" @click="createMission()">Create New Mission</button>
            </b-col>
        </b-row>
        <b-row id="missions">
            <table id="missionstable" class="table table-striped">
                <tr v-for="mission in missions" :key="stringMissionId(mission)">
                    <mission v-on:deleteEvent="getAllMissions" v-bind:mission-from-parent="mission"  :credentials-list=credentialsList></mission>
                </tr>
            </table>
        </b-row>
    </b-container>
</template>

<script>
    import {AXIOS} from './http-common'
    import Mission from "./Mission";

    //commento
    export default {
        name: 'twitter-mission-list',
        components: {
            Mission
        },
        data() {
            return {
                msg: 'HowTo call REST-Services:',
                response: {},
                errors: [],
                missions: [],
                credentialsList: []
            }
        },
        mounted() {
            this.getAllMissions()
            this.getAllCredentials()
        },
        computed: {        },
        methods: {
            createMission() {
                let now = new Date(Date.now())
                let nowString = now.toISOString()
                this.missions.unshift({
                    "creationDateTime" : nowString.substr(0,nowString.length - 1),
                    "startDateTime" :  nowString.substr(0,nowString.length - 1),
                    "endDateTime" : now.addDays(10).toISOString().substr(0,nowString.length - 1),
                    "isActive" : false,
                    "credentialsId" : "default",
                    "type" : "STREAM"
                })
            },
            getAllMissions() {
                AXIOS.get(`mission/twitter/_all`)
                    .then(response => {
                        // JSON responses are automatically parsed.
                        this.response = response.data
                        this.missions = this.response
                        console.log(response.data)
                    })
                    .catch(e => {
                        this.errors.push(e)
                    })
            },
            getAllCredentials() {
                AXIOS.get(`credentials/twitter/_all`)
                    .then(response => {
                        this.response = response.data
                        this.credentialsList = this.response
                        console.log(response.data)
                    })
                    .catch(e => {
                        this.errors.push(e)
                    })
            },
            stringMissionId(mission) {
                if (!!mission && !!mission.id) return mission.id.toString()
                else return null
            }
        }
    }
</script>

<style scoped>
    #commandsrow {
        padding-bottom: 50px;
    }
</style>