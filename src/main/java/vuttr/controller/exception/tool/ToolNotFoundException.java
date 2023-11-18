package vuttr.controller.exception.tool;

public class ToolNotFoundException extends Exception {
    private String message;
    public ToolNotFoundException(Long id) {
        this.message = String.format("Tool with id %s was not found", String.valueOf(id));
    }
    public ToolNotFoundException(String title) {
        this.message = String.format("Tool with title %s was not found", title);
    }

    public static ToolNotFoundException createWith(Long id) {
        return new ToolNotFoundException(id);
    }
    public static ToolNotFoundException createWith(String title) {
        return new ToolNotFoundException(title);
    }

    public String getMessage() {
        return this.message;
    }
}
