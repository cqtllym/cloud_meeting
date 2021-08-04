export default {
  namespaced: true,
  state: {
    username: undefined,
    userId: undefined,
    companyName: undefined,
    companyId: undefined,
    sex: undefined,
    telephone: undefined,
    email: undefined,
    count: undefined,
    roles: null,
    routesConfig: null,
  },
  getters: {
    username: state => {
      if (!state.username) {
        try {
          const username = localStorage.getItem(process.env.VUE_APP_USERNAME_KEY)
          state.username = JSON.parse(username)
        } catch (e) {
          console.error(e)
        }
      }
      return state.username
    },
    userId: state => {
      if (!state.userId) {
        try {
          const userId = localStorage.getItem(process.env.VUE_APP_USER_ID_KEY)
          state.userId = JSON.parse(userId)
        } catch (e) {
          console.error(e)
        }
      }
      return state.userId
    },
    companyName: state => {
      if (!state.companyName) {
        try {
          const companyName = localStorage.getItem(process.env.VUE_APP_COMPANY_NAME_KEY)
          state.companyName = JSON.parse(companyName)
        } catch (e) {
          console.error(e)
        }
      }
      return state.companyName
    },
    companyId: state => {
      if (!state.companyId) {
        try {
          const companyId = localStorage.getItem(process.env.VUE_APP_COMPANY_ID_KEY)
          state.companyId = JSON.parse(companyId)
        } catch (e) {
          console.error(e)
        }
      }
      return state.companyId
    },
    sex: state => {
      if (!state.sex) {
        try {
          const sex = localStorage.getItem(process.env.VUE_APP_SEX_KEY)
          state.sex = JSON.parse(sex)
        } catch (e) {
          console.error(e)
        }
      }
      return state.sex
    },
    telephone: state => {
      if (!state.telephone) {
        try {
          const telephone = localStorage.getItem(process.env.VUE_APP_TELEPHONE_KEY)
          state.telephone = JSON.parse(telephone)
        } catch (e) {
          console.error(e)
        }
      }
      return state.telephone
    },
    email: state => {
      if (!state.email) {
        try {
          const email = localStorage.getItem(process.env.VUE_APP_EMAIL_KEY)
          state.email = JSON.parse(email)
        } catch (e) {
          console.error(e)
        }
      }
      return state.email
    },
    count: state => {
      if (!state.count) {
        try {
          const count = localStorage.getItem(process.env.VUE_APP_COUNT_KEY)
          state.count = JSON.parse(count)
        } catch (e) {
          console.error(e)
        }
      }
      return state.count
    },
    roles: state => {
      if (!state.roles) {
        try {
          const roles = localStorage.getItem(process.env.VUE_APP_ROLES_KEY)
          state.roles = JSON.parse(roles)
          state.roles = state.roles ? state.roles : []
        } catch (e) {
          console.error(e.message)
        }
      }
      return state.roles
    },
    routesConfig: state => {
      if (!state.routesConfig) {
        try {
          const routesConfig = localStorage.getItem(process.env.VUE_APP_ROUTES_KEY)
          state.routesConfig = JSON.parse(routesConfig)
          state.routesConfig = state.routesConfig ? state.routesConfig : []
        } catch (e) {
          console.error(e.message)
        }
      }
      return state.routesConfig
    }
  },
  mutations: {
    setUserName(state, username) {
      state.username = username
      localStorage.setItem(process.env.VUE_APP_USERNAME_KEY, JSON.stringify(username))
    },
    setUserId(state, userId) {
      state.userId = userId
      localStorage.setItem(process.env.VUE_APP_USER_ID_KEY, JSON.stringify(userId))
    },
    setCompanyName(state, companyName) {
      state.companyName = companyName
      localStorage.setItem(process.env.VUE_APP_COMPANY_NAME_KEY, JSON.stringify(companyName))
    },
    setCompanyId(state, companyId) {
      state.companyId = companyId
      localStorage.setItem(process.env.VUE_APP_COMPANY_ID_KEY, JSON.stringify(companyId))
    },
    setSex(state, sex) {
      state.sex = sex
      localStorage.setItem(process.env.VUE_APP_SEX_KEY, JSON.stringify(sex))
    },
    setTelephone(state, telephone) {
      state.telephone = telephone
      localStorage.setItem(process.env.VUE_APP_TELEPHONE_KEY, JSON.stringify(telephone))
    },
    setEmail(state, email) {
      state.email = email
      localStorage.setItem(process.env.VUE_APP_EMAIL_KEY, JSON.stringify(email))
    },
    setCount(state, count) {
      state.count = count
      localStorage.setItem(process.env.VUE_APP_COUNT_KEY, JSON.stringify(count))
    },
    setRoles(state, roles) {
      state.roles = roles
      localStorage.setItem(process.env.VUE_APP_ROLES_KEY, JSON.stringify(roles))
    },
    setRoutesConfig(state, routesConfig) {
      state.routesConfig = routesConfig
      localStorage.setItem(process.env.VUE_APP_ROUTES_KEY, JSON.stringify(routesConfig))
    }
  }
}
