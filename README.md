This is a simple gateway application configured to route requests to some backend service. It has
a single page which submits POST requests to that same service.

The gateway uses Spring Security to provide CSRF protection. If CSRF protection is disabled,
everything works properly. But when it's enabled, the form POST on the gateway page hangs until
it times out.

## To reproduce
1. Configure the gateway to point to some webapp. By default it uses `https://webhook.site`, but 
the only thing that matters is that you can verify the requests are received.
    ```yaml
    spring:
      cloud:
        gateway:
          routes:
            # See https://webhook.site/#!/ff36f3af-5499-428d-96b2-f0f49469c5e3
            - id: example
              uri: https://webhook.site
              predicates:
                - Path=/**
              filters:
                - PrefixPath=/ff36f3af-5499-428d-96b2-f0f49469c5e3
    ```
   > Note: If using a url with a path, include it in a PrefixPath filter as shown above.
2. Run the gateway.
3. Load the Gateway webpage at `http://localhost:9999`.
4. There are two examples of POST requests to the Rest API, a form POST and an AJAX POST. Try
submitting both.

### Expected Behavior
Both requests should work.

### Actual Behavior
The AJAX POST works. 
The form POST hangs.

## Running without CSRF protection
CSRF protection can be disabled by setting `csrf.enabled=false` in the gateway application.yml.
Perform the above steps with CSRF disabled to verify that both POST requests work as expected.
