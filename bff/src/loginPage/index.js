const koa = require('koa');
const axios = require('axios')
const fs = require('fs');
const mount = require('koa-mount');
const fstatic = require('koa-static');
const ConfigurationManager = require('./ConfigurationManager')
const bodyParser = require('koa-bodyparser');



const app = new koa();
const loginPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');


app.use(bodyParser());


app.use(
    fstatic(__dirname + '/source/')
);

app.use(
    mount('/submit/', async (ctx) => {
        const axiosConfig = {
            headers: {
                timeout: 3000,
                Cookie: ConfigurationManager.defaultCookieInstanceGenerator(ctx)
            }
        };
        let dataBody;
        if (ctx.method === 'POST') {
            dataBody = ctx.request.body
            // console.log(dataBody)
            let resultCode;
            await axios.post('http://127.0.0.1:8080/login', dataBody, axiosConfig)
                .then((res) => {
                    resultCode = res.data.code
                    if (resultCode === 200) {
                        let tokenValue = res.data.data.token
                        let userId = res.data.data.id
                        console.log(res.data)
                        ctx.cookies.set(ConfigurationManager.Constants.tokenNameInCookie, tokenValue)
                        ctx.cookies.set(ConfigurationManager.Constants.idNameInCookie, userId)
                    } else {
                        console.log(res.data)
                    }

                })
                .catch((reason) => {
                    console.log(reason)
                })
                .finally(() => {
                    console.log("Login: " + resultCode)

                })
        } else {
            let loginResultPageContent;
            let resultCode;
            await axios.get('http://127.0.0.1:8080/isAuthenticated/', axiosConfig)
                .then((res) => {
                   resultCode = res.data.code
                    if (resultCode === 200) {
                        loginResultPageContent = `<html lang="">Status: Logged in</html>`
                        console.log(res.data)
                    } else {
                        console.log(res.data)
                        loginResultPageContent = `<html>Status: Not logged in</html>`
                    }
                })
                .catch((reason) => {
                    resultCode = reason.response.status
                    console.log(reason.response.data)
                    loginResultPageContent = `<html>${reason.response.data.message}</html>`
                })
                .finally(() => {
                    console.log("Logged in status maintaining: " + resultCode)
                    ctx.body = loginResultPageContent
                })
        }    
        ctx.status= 200
                
    })
    
);

app.use(
    mount('/registry/', async (ctx) => {
            let dataBody;
            if (ctx.method === 'POST') {
                dataBody = ctx.request.body
                console.log(dataBody)
                await axios.post(ConfigurationManager.Constants.urlRegistryServer, dataBody)
                    .then((res) => {
                        let resultCode = res.data.code
                        if (resultCode === 200) {
                            ctx.response.status = 200
                        } else {
                            ctx.response.status = 304
                        }
                        console.log(res.data)
                        console.log("Registry: " + resultCode)
                    })
                    .catch((reason) => {
                        console.log(reason)
                    });
            }
    }
    )
);

app.use(
    mount('/', async (ctx) => {
        ctx.body = loginPageContent
    })
);

module.exports = app;
