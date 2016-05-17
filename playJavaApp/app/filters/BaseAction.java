package filters;

import play.http.ActionCreator;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;
import java.util.concurrent.CompletionStage;

/**
 * Created by Administrator on 2016/5/10.
 */
public class BaseAction implements ActionCreator {
    @Override
    public Action wrapAction(Action action) {
        return null;
    }

    @Override
    public Action createAction(Http.Request request, Method actionMethod) {

        return new Action.Simple() {
            @Override
            public CompletionStage<Result> call(Http.Context ctx) {
                
                return delegate.call(ctx);
            }
        };
    }
}
