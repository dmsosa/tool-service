package vuttr.controller.exception.tool;

public class ToolExistsException extends Exception {
    private String message;
    public ToolExistsException(Long id) {
        this.message = String.format("Tool with id %s already exists", String.valueOf(id));
    }
    public ToolExistsException(String title) {
        this.message = String.format("Tool with title %s already exists", title);
    }

    public static ToolExistsException createWith(Long id) {
        return new ToolExistsException(id);
    }
    public static ToolExistsException createWith(String title) {
        return new ToolExistsException(title);
    }

    public String getMessage() {
        return this.message;
    }

}
