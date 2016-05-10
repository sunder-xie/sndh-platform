package filters;

import play.api.mvc.Handler;
import play.http.HandlerForRequest;
import play.http.HttpRequestHandler;
import play.libs.streams.Accumulator;
import play.mvc.EssentialAction;
import play.mvc.Http;
import play.mvc.Results;
import play.routing.Router;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/5/4.
 */
public class RequestHandler implements HttpRequestHandler {
    private final Router router;

    @Inject
    public RequestHandler(Router router) {
        this.router = router;
    }
    @Override
    public HandlerForRequest handlerForRequest(Http.RequestHeader request) {
        Handler handler = router.route(request).orElseGet(() ->
                EssentialAction.of(req -> Accumulator.done(Results.notFound()))
        );
        System.out.println("----request.path----"+request.path());
        return new HandlerForRequest(request, handler);
    }
}
