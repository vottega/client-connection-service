package vottega.sse_server.filter

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class HeaderToContextFilter : WebFilter {
  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    return chain.filter(exchange)
      .contextWrite { ctx ->
        ctx.put("inboundHeaders", exchange.request.headers)
      }
  }
}

