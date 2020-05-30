function defaultCookieInstanceGenerator(ctx){
    if (ctx == null) {
        log.info("Context empty")
        return null;
    }
    return `userID=${ctx.cookies.get('userID')}; AUTH_TOKEN=${ctx.cookies.get('AUTH_TOKEN')}; JSESSIONID=${ctx.cookies.get('JSESSIONID')}`
}

const Constants = {
    urlRegistryServer : 'http://127.0.0.1:8080/registry',
    tokenNameInCookie : 'AUTH_TOKEN',
    idNameInCookie: 'userID'
}

module.exports = {defaultCookieInstanceGenerator, Constants}