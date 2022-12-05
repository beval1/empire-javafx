package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.views.RenderingView;
import javafx.application.Platform;

import java.io.IOException;

import static com.beval.empirejavafx.config.AppConstants.TIME_BETWEEN_RENDERS;

public class RenderingManager {
    private RenderingManager() {
    }

    private static RenderingView renderingView = null;

    public static void setRenderingView(RenderingView renderingView) {
        RenderingManager.renderingView = renderingView;
        render();
    }

    private static void render() {
        if (renderingView == null) {
            throw new IllegalStateException("RenderingView is null!");
        }

        new Thread(() -> {
            while (true) {
                try {
                    Platform.runLater(() -> {
                        try {
                            renderingView.render();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    Thread.sleep(TIME_BETWEEN_RENDERS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
