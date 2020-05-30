function defaultCookieInstanceGenerator(ctx){
    if (ctx == null) {
        log.info("Context empty")
        return null;
    }
    return `userID=${ctx.cookies.get('userID')}; AUTH_TOKEN=${ctx.cookies.get('AUTH_TOKEN')}; JSESSIONID=${ctx.cookies.get('JSESSIONID')}`
}

const Constants = {
        blankService : 'http://127.0.0.1:8080/blank'
}

module.exports = {defaultCookieInstanceGenerator, Constants}