import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
import Bootstrap from '@/components/Bootstrap'
import User from '@/components/User'
import Mission from '@/components/Mission'
import TwitterStream from '@/components/TwitterStream'
import MissionsList from "./components/MissionsList";
import CredentialsList from "./components/CredentialsList";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'default',
      component: TwitterStream
    },
    {
      path: '/callservice',
      name: 'Service',
      component: Service
    },
    {
      path: '/bootstrap',
      name: 'Bootstrap',
      component: Bootstrap
    },
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/mission',
      name: 'Mission',
      component: Mission
    },
    {
      path: '/missionslist',
      name: 'MissionsList',
      component: MissionsList
    },
    {
      path: '/twitterstream',
      name: 'TwitterStream',
      component: TwitterStream
    },
    {
      path: '/credentialslist',
      name: 'CredentialsList',
      component: CredentialsList
    }

  ]
})
