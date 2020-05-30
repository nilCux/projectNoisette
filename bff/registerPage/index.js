const koa = require('koa');
const router = require('koa-router')
const axios = require('axios')
const fs = require('fs');
const mount = require('koa-mount');
const static = require('koa-static');

const app = new koa();
const registerPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');
var registryResult = `<html><meta http-equiv="refresh" content="0.5">Registrating...</html>`

app.use(mount('/favicon.ico',ctx => {
    // ignore favicon
    ctx.state = 200
  }));

app.use(
    static(__dirname + '/source/')
);

app.use(
    mount('/submit/', async (ctx) => {

        ctx.request.req.on('data', async function(data){ 
            dataBody = JSON.parse(data.toString())
            await axios.post('http://127.0.0.1:8080/registry', dataBody)
                .then((res)=>{
                    resultCode = res.data.code
                    if (resultCode === 200) {
                        registryResult =`<html>Successful Registration</html>`
                    } else {
                        registryResult =`<html>Failed Registration</html>`
                    }
                    console.log(res.data)
                    console.log("Registry: " + resultCode)
                })
                .catch((reason) => {console.log(reason)});
        })
        ctx.response.body = registryResult
        ctx.status = 200
    }
    )
);

app.use(
    mount('/', async (ctx) => {
        ctx.body = registerPageContent
    })
);

module.exports = app;