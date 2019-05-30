<template>
    <div v-show="everythingIsReady" class="tweetCard flex w-100">
        <transition name="slide">
        <b-row align-v="center" class="flex-row">
            <b-col class="avatarImage" cols="1">
                <b-img :src="tweet.user.profile_image_url" class="avatar" width="70" height="70" alt="avatar image"></b-img>
            </b-col>
            <b-col class="avatarContainer" cols="2">
                <div class="author" >
                        {{ tweet.user.screen_name}}
                </div>
            </b-col>
            <b-col class="tweetHead" cols="2">
                <div>
                    {{ created_at}}
                </div>
                <div class="tweetId">
                    {{ tweet.id_str }}
                </div>
            </b-col>
            <b-col class="tweetBody" cols="7">
                <div class="tweetContent">
                    {{ tweet.text !== undefined ? tweet.text : tweet.full_text}}
                </div>
            </b-col>
        </b-row>
        </transition>
    </div>
</template>

<script>
    export default {
        name: 'tweet',
        props: {tweetString: null},
        computed: {
            tweet() {
                if (!!this.tweetString) {
                    return JSON.parse(this.tweetString)
                } else return null
            },
            created_at(){
               return this.tweet.created_at.substring(0, 20)
            },
            everythingIsReady() {
                return !!this.tweet.created_at
            }
        }
    }
</script>

<style scoped>

    .avatar {
        margin-top: 3px;
        border-radius: 50%;
    }

    .tweetBody {
        margin-top: 5px;
    }

    .tweetHead {
        font-size: 13px;
    }

    .author {
        font-size: 14px;
        font-weight: bold;
    }

    .tweetContent {
        font-size: 14px;
        line-height: 20px;
        margin-top: 8px;
    }

    .tweetCard {
        width: 500px;
    }
    .tweetId {
        margin-top: 10px;
    }
</style>