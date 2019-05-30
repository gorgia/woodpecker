<template>
    <div class="mission col-10 container-fluid" v-if="!!mission">
        <b-container class="missioncontainer border border-info col-10">
            <b-row class="flex-row">
                <input type="checkbox" id="isActive" :disabled="!isEditable" v-model="mission.isActive" class="col-1">
                <label for="isActive" class="firstrowlabels">isActive</label>
                <div class="col-1"></div>
                <label for="missionTypeFormSelect" class="firstrowlabels"
                       id="missionTypeFormSelectLabel">missionType:</label>
                <b-form-select :disabled="!isEditable" id="missionTypeFormSelect" class="col-3" v-model="mission.type">
                    <option v-for="missionT in missionTypes">{{missionT}}</option>
                </b-form-select>
                <div class="col-1"></div>
                <label for="credentialsFormSelect" class="firstrowlabels"
                       id="credentialsFormSelectLabel">credentials:</label>
                <b-form-select :disabled="!isEditable" id="credentialsFormSelect" class="col-3"
                               v-model="mission.credentialsId">
                    <option v-for="credentials in credentialsList">{{credentials.name}}</option>
                </b-form-select>
            </b-row>
            <b-row class="flex-row">
                <b-col id="missionname" class="d-flex">
                    <b-form-input :readonly="!isEditable" type="text" id="inputmissionname"
                                  v-model="mission.missionName" placeholder="mission name"/>
                    <b-form-input :readonly="!isEditable" type="text" id="inputmissiongroup"
                                  v-model="mission.missionGroup" placeholder="mission group"/>
                </b-col>
                <b-col id="missiondate" class="d-flex">
                    <VueCtkDateTimePicker :disabled="!isEditable" v-model="mission.startDateTime" label="Start Date"
                                          format="YYYY-MM-DDTHH:mm:ss.SSS"/>
                    <VueCtkDateTimePicker :disabled="!isEditable" v-model="mission.endDateTime" label="End Date"
                                          format="YYYY-MM-DDTHH:mm:ss.SSS"/>
                </b-col>
            </b-row>
            <b-row id="querycontainer" class="flex-row">
                <label for="inputquery">Comma-deilimited filter queries:</label>
                <b-form-textarea :readonly="!isEditable" rows="3" max-rows="10" id="inputquery" v-model="mission.query"
                                 placeholder="MISSION QUERY"></b-form-textarea>
            </b-row>
            <b-row id="controlbuttonrow" class="flex-row justify-content">
                <b-button class="col-1 controlbutton mr-3" variant="primary" size="lg" v-if="!isEditable"
                          @click="toggleEditable()">Edit
                </b-button>
                <b-button class="col-1 controlbutton mr-3" variant="success" size="lg" v-if="isEditable"
                          @click="toggleEditable(); saveMission()">Save
                </b-button>
                <div></div>
                <b-button class="col-1 controlbutton mr-3" variant="secondary" size="lg" v-if="isEditable"
                          @click="toggleEditable()">Undo
                </b-button>
                <div></div>
                <b-button :disabled="mission.isActive" class="col-1 controlbutton mr-3" variant="danger" size="lg" v-if="isEditable&&!!mission.id" @click="toggleEditable(); deleteMission()">
                    Delete
                </b-button>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import {AXIOS} from './http-common'
    import VueCtkDateTimePicker from 'vue-ctk-date-time-picker';
    import 'vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css';

    Date.prototype.addDays = function (days) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
    }

    export default {
        name: 'mission',
        props: {
            missionFromParent: null,
            credentialsList: {
                type: Array,
                required: true
            }
        },
        components: {
            VueCtkDateTimePicker
        },

        data() {
            return {
                errors: [],
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false,
                isEditable: false,
                missionTypes: ["STREAM", "TIMELINE"],
                mission: {...this.missionFromParent}
            }
        },
        computed: {
            everythingIsReady() {
                return !!this.mission
            }
            // mission: {
            //     get: function () {
            //         return this.missionFromParent
            //     },
            //     set: function (newValue) {
            //         //this.$emit('update:missionFromParent', newValue)
            //         this.missionFromParent = newValue
            //     }
            // }
        },
        methods: {
            // Fetches posts when the component is created.
            saveMission() {
                console.log("Mission to be sent:\n" + JSON.stringify(this.mission))
                AXIOS.post(`/mission/twitter`, this.mission)
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
            deleteMission() {
                AXIOS.delete(`/mission/twitter`, {data: this.mission}).then((res) => {
                        console.log(res)
                        this.$emit('deleteEvent', this.mission)
                    }
                )
            },
            getDefaultEndDate() {
                let today = new Date()
                return new Date(today.setDate(today.getDate() + 10)).toISOString()
            },
            toggleEditable() {
                this.isEditable = !this.isEditable
            }
        }
    }

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .mission {
        padding-top: 40px
    }

    .missioncontainer {
        border-color: lightcoral;
    }

    .firstrowlabels {
        padding-right: 20px;
    }

    #controlbuttonrow {
        padding-top: 40px
    }

    .controlbutton {
        padding-right: 12px;
    }
</style>
