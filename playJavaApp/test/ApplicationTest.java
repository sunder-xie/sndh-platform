import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import controllers.HomeController;
import org.junit.*;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import services.inter.MessageService;

import javax.inject.Inject;

import static play.test.Helpers.*;
import static org.junit.Assert.*;


/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class ApplicationTest {
    private FakeApplication fakeApplication;

//    @Override
//    protected Application provideApplication() {
//        return new GuiceApplicationBuilder().configure("play.http.router", "javaguide.tests.Routes").build();
//    }

    @Before
    public void setUp() {
        fakeApplication = (FakeApplication)fakeApplication(inMemoryDatabase());
        start(fakeApplication);
    }

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void Test1() {
        Result result = route(controllers.routes.HomeController.index());
        assertEquals(OK, result.status());
    }

    @Test
    public void renderTemplate() {
//        Content html = views.html.index.render("Your new application is ready.");
//        assertEquals("text/html", html.contentType());
//        assertTrue(html.body().contains("Your new application is ready."));

    }

    @After
    public void tearDown(){
        stop(fakeApplication);
    }

}
