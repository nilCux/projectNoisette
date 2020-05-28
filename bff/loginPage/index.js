const koa = require('koa');
const axios = require('axios')
const fs = require('fs');
const mount = require('koa-mount');
const static = require('koa-static');

const app = new koa();
const loginPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');
var loginResult


app.use(mount('/favicon.ico',ctx => {
  // ignore favicon
  ctx.state = 200
}));

app.use(
    static(__dirname + '/source/')
);

app.use(
    mount('/submit/', async(ctx) => {

        ctx.request.req.on('data', async function(data){ 
            dataBody = JSON.parse(data.toString())
            await axios.post('http://127.0.0.1:8080/login', dataBody)
                .then((res)=>{
                    console.log(res)
                    loginResult = res.data
                    console.log("Login: " + loginResult.code)
                })
                .catch((reason) => {console.log(reason)});    
        })
        ctx.response.body = `<html>login: ${loginResult.code}</html>`      
        ctx.status = 200
    })
);

app.use(
    mount('/', async (ctx) => {
        ctx.body = loginPageContent
    })
);

module.exports = app;