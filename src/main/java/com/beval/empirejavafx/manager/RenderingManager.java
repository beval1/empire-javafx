package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.views.RenderingView;
import javafx.application.Platform;

import java.io.IOException;

public class RenderingManager {
    private RenderingManager() {
    }

    private static RenderingView renderingView = null;

    public static void setRenderingView(RenderingView renderingView) throws IOException {
        RenderingManager.renderingView = renderingView;
        render();
    }

    private static void render() throws IOException {
        if (renderingView == null) {
            throw new IllegalStateException("RenderingView is null!");
        }

        new Thread(() -> {
            while (true) {
                try {
                    Platform.runLater(() -> {
                        try {
                            renderingView.render();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
