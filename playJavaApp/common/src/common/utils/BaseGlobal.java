package common.utils;

import play.GlobalSettings;
import play.api.Application;
import play.api.http.HttpConfiguration;
import play.api.mvc.*;
import play.mvc.Action;
import play.mvc.Http;
import scala.Function1;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.Future;

import java.lang.reflect.Method;
import java.util.concurrent.CompletionStage;

/**
 * Created by Administrator on 2016/5/10.
 */
public class BaseGlobal extends GlobalSettings {
    public BaseGlobal() {
        super();
    }

    @Override
    public void beforeStart(play.Application app) {
        super.beforeStart(app);
    }

    @Override
    public void onStart(play.Application app) {
        super.onStart(app);
    }

    @Override
    public void onStop(play.Application app) {
        super.onStop(app);
    }

    @Override
    public CompletionStage<play.mvc.Result> onError(Http.RequestHeader request, Throwable t) {
        return super.onError(request, t);
    }

    @Override
    public Action onRequest(Http.Request request, Method actionMethod) {
        return super.onRequest(request, actionMethod);
    }

    @Override
    public Handler onRouteRequest(Http.RequestHeader request) {
        return super.onRouteRequest(request);
    }

    @Override
    public CompletionStage<play.mvc.Result> onHandlerNotFound(Http.RequestHeader request) {
        return super.onHandlerNotFound(request);
    }

    @Override
    public CompletionStage<play.mvc.Result> onBadRequest(Http.RequestHeader request, String error) {
        return super.onBadRequest(request, error);
    }

    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return super.filters();
    }
}
