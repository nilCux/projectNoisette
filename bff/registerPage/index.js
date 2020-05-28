const koa = require('koa');
const axios = require('axios')
const fs = require('fs');
const mount = require('koa-mount');
const static = require('koa-static');

const app = new koa();
const registerPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');
var registryResult

app.use(
    static(__dirname + '/source/')
);

app.use(
    mount('/submit/', async (ctx) => {

        ctx.request.req.on('data', async function(data){ 
            dataBody = JSON.parse(data.toString())
            await axios.post('http://127.0.0.1:8080/registry', dataBody)
                .then((res)=>{
                    registryResult = res.data
                    console.log("Registry: " + registryResult)
                })
                .catch((reason) => {console.log(reason)});
        })
        ctx.response.body = `<html>Registry: ${registryResult}</html>`      
        ctx.status = 200
    })
);

app.use(
    mount('/', async (ctx) => {
        ctx.body = registerPageContent
    })
);

module.exports = app;