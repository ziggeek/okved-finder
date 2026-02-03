package com.okved.finder.view;//package com.okved.finder.view;

import com.okved.finder.controller.response.OkvedSearchResponse.OkvedResponse;
import com.okved.finder.entity.Okved;
import com.okved.finder.exception.PhoneNormalizationException;
import com.okved.finder.service.OkvedService;
import com.okved.finder.utils.PhoneNormalizationUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Главная страница приложения с UI для поиска ОКВЭД по номеру телефона.
 */
@Slf4j
@Route("")
public class OkvedFinderView extends VerticalLayout {

    private OkvedService okvedService;
    private Grid<OkvedResponse> table = new Grid<>(OkvedResponse.class, false);
    private Button searchButton = new Button("Поиск");
    private TextField phoneNumberField = new TextField();
    private TextField normalizedPhoneField = new TextField();
    private TextField matchLengthField = new TextField();

    @Autowired
    public OkvedFinderView(OkvedService okvedService) {
        this.okvedService = okvedService;

        phoneNumberField.setPlaceholder("Введите номер телефона");
        phoneNumberField.setHelperText("+7 (922) 222 22 22, 89225559033, 7(922)2221232");
        phoneNumberField.setWidthFull();
        phoneNumberField.setAutofocus(true);
        phoneNumberField.setAutoselect(true);
        phoneNumberField.setClearButtonVisible(true);
        phoneNumberField.setRequired(true);


        normalizedPhoneField.setLabel("Нормализованный номер телефона");
        normalizedPhoneField.setWidthFull();
        normalizedPhoneField.setReadOnly(true);


        matchLengthField.setLabel("Совпало цифр");
        matchLengthField.setWidthFull();
        matchLengthField.setReadOnly(true);


        // Настраиваем колонки таблицы (пример)
        table.addColumn(OkvedResponse::okvedCode).setHeader("Код ОКВЭД");
        table.addColumn(OkvedResponse::okvedName).setHeader("Наименование");

        searchButton.addClickListener(e -> {
            searchButton.setEnabled(false);
            try {
                String phoneNumber = phoneNumberField.getValue();
                if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                    Notification.show("Введите номер телефона", 3000, Notification.Position.MIDDLE);
                    return;
                }

                List<Okved> okveds = okvedService.findOkvedByPhoneNumber(phoneNumber);
                int lenght = okveds.size() == 1 ? okveds.stream().findAny().get().getCode().length() : 0;
                normalizedPhoneField.setValue(PhoneNormalizationUtils.normalize(phoneNumber));
                matchLengthField.setValue("" + lenght);
                table.setItems(OkvedResponse.from(okveds));


                if (okveds.isEmpty()) {
                    Notification.show("По данному номеру телефона ОКВЭД не найден", 3000, Notification.Position.MIDDLE);
                }

                if (okveds.size() > 1) {
                    Notification.show("Ничего не найдено. Применена стратегия вывода 10 ОКВЭД кодов", 3000, Notification.Position.MIDDLE);
                }


            } catch (PhoneNormalizationException ex) {
                log.error("Ошибка нормализации телефона", ex);
                Notification.show("Неверный формат номера телефона", 3000, Notification.Position.MIDDLE);
            } catch (Exception ex) {
                log.error("Ошибка при поиске ОКВЭД", ex);
                Notification.show("Произошла ошибка при поиске", 3000, Notification.Position.MIDDLE);
            }
            searchButton.setEnabled(true);
        });

        phoneNumberField.addKeyPressListener(Key.ENTER, e -> searchButton.click());

        add(new H1("Поиск ОКВЭД по номеру мобильного телефона"),
                new HorizontalLayout(phoneNumberField, searchButton),
                new HorizontalLayout(normalizedPhoneField, matchLengthField),
                table);

        table.setItems(Collections.emptyList());
    }
}