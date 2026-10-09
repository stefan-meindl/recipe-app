/* @ds-bundle: {"format":4,"namespace":"SubtleGradientDesignSystem_b07db2","components":[{"name":"Badge","sourcePath":"components/badges/Badge.jsx"},{"name":"PromoBanner","sourcePath":"components/badges/PromoBanner.jsx"},{"name":"AppDownloadBadge","sourcePath":"components/buttons/AppDownloadBadge.jsx"},{"name":"Button","sourcePath":"components/buttons/Button.jsx"},{"name":"Card","sourcePath":"components/cards/Card.jsx"},{"name":"CodeBlock","sourcePath":"components/code/CodeBlock.jsx"},{"name":"FeatureIconTile","sourcePath":"components/content/FeatureIconTile.jsx"},{"name":"IndustryTile","sourcePath":"components/content/IndustryTile.jsx"},{"name":"TestimonialCard","sourcePath":"components/content/TestimonialCard.jsx"},{"name":"CookingStep","sourcePath":"components/cooking/CookingStep.jsx"},{"name":"Ingredient","sourcePath":"components/cooking/Ingredient.jsx"},{"name":"StepTimer","sourcePath":"components/cooking/StepTimer.jsx"},{"name":"FaqItem","sourcePath":"components/faq/FaqItem.jsx"},{"name":"Footer","sourcePath":"components/footer/Footer.jsx"},{"name":"FooterLink","sourcePath":"components/footer/FooterLink.jsx"},{"name":"ContactFormPanel","sourcePath":"components/forms/ContactFormPanel.jsx"},{"name":"TextArea","sourcePath":"components/forms/TextArea.jsx"},{"name":"TextInput","sourcePath":"components/forms/TextInput.jsx"},{"name":"ICON_NAMES","sourcePath":"components/icons/Icon.jsx"},{"name":"Icon","sourcePath":"components/icons/Icon.jsx"},{"name":"TopNav","sourcePath":"components/navigation/TopNav.jsx"},{"name":"PricingCard","sourcePath":"components/pricing/PricingCard.jsx"},{"name":"ShoppingList","sourcePath":"components/shopping/ShoppingList.jsx"},{"name":"ShoppingListItem","sourcePath":"components/shopping/ShoppingListItem.jsx"},{"name":"CtaBanner","sourcePath":"components/signature/CtaBanner.jsx"},{"name":"GradientStripe","sourcePath":"components/signature/GradientStripe.jsx"},{"name":"HeroBand","sourcePath":"components/signature/HeroBand.jsx"},{"name":"LogoWall","sourcePath":"components/stats/LogoWall.jsx"},{"name":"StatCell","sourcePath":"components/stats/StatCell.jsx"},{"name":"PillTabs","sourcePath":"components/tabs/PillTabs.jsx"},{"name":"SegmentedTabs","sourcePath":"components/tabs/SegmentedTabs.jsx"}],"sourceHashes":{"components/badges/Badge.jsx":"e3bdce33c555","components/badges/PromoBanner.jsx":"3dcbb61e632c","components/buttons/AppDownloadBadge.jsx":"6d62acf60591","components/buttons/Button.jsx":"71645f1aa91c","components/cards/Card.jsx":"385e765b629b","components/code/CodeBlock.jsx":"e2a2187e62f3","components/content/FeatureIconTile.jsx":"2a49c6b4ef06","components/content/IndustryTile.jsx":"2bac030204a6","components/content/TestimonialCard.jsx":"c39f0ac3f81c","components/cooking/CookingStep.jsx":"7fcb5f556cb2","components/cooking/Ingredient.jsx":"b8728be3d91c","components/cooking/StepTimer.jsx":"61eb0580f826","components/faq/FaqItem.jsx":"8509530d9511","components/footer/Footer.jsx":"4aca2892b88f","components/footer/FooterLink.jsx":"4f9409274f60","components/forms/ContactFormPanel.jsx":"337ec910704f","components/forms/TextArea.jsx":"3d434c75e5b0","components/forms/TextInput.jsx":"dcdea7d04873","components/icons/Icon.jsx":"555f83e4ebe3","components/navigation/TopNav.jsx":"6527c094bae7","components/pricing/PricingCard.jsx":"079290775950","components/shopping/ShoppingList.jsx":"ae527bd33180","components/shopping/ShoppingListItem.jsx":"79ef26bac861","components/signature/CtaBanner.jsx":"a66c7ae10a9f","components/signature/GradientStripe.jsx":"5eb6f2748077","components/signature/HeroBand.jsx":"29949f612eb7","components/stats/LogoWall.jsx":"4868da6f2319","components/stats/StatCell.jsx":"8a23dd3028f3","components/tabs/PillTabs.jsx":"df93e1f86eae","components/tabs/SegmentedTabs.jsx":"a7ce066a1f3d","ui_kits/recipe-app/App.jsx":"13d106debf50","ui_kits/recipe-app/CookingScreen.jsx":"6b3c20dcc1c7","ui_kits/recipe-app/RecipeScreen.jsx":"6652d1fd9388","ui_kits/recipe-app/RecipesScreen.jsx":"7fd19f64d591","ui_kits/recipe-app/ShoppingScreen.jsx":"b81bfa1734fa","ui_kits/recipe-app/data.jsx":"9d1f47cef5c8","ui_kits/recipe-app/ios-frame.jsx":"24642b887be3","ui_kits/recipe-app/parts.jsx":"43317152e0f3"},"inlinedExternals":[],"unexposedExports":[{"name":"formatClock","sourcePath":"components/cooking/StepTimer.jsx"}]} */

(() => {

const __ds_ns = (window.SubtleGradientDesignSystem_b07db2 = window.SubtleGradientDesignSystem_b07db2 || {});

const __ds_scope = {};

(__ds_ns.__errors = __ds_ns.__errors || []);

// components/cards/Card.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
const VARIANTS = {
  base: {
    background: 'var(--surface-card)',
    borderColor: 'var(--border-subtle)',
    padding: 'var(--space-xl)'
  },
  feature: {
    background: 'var(--surface-card)',
    borderColor: 'var(--border-subtle)',
    padding: 'var(--space-xxl)'
  },
  cream: {
    background: 'var(--surface-warm)',
    borderColor: 'var(--border-warm)',
    color: 'var(--text-on-warm)',
    padding: 'var(--space-xxl)'
  },
  'cream-soft': {
    background: 'var(--surface-warm-soft)',
    borderColor: 'transparent',
    color: 'var(--text-on-warm)',
    padding: 'var(--space-xxl)'
  },
  'feature-product': {
    background: 'var(--surface-card)',
    borderColor: 'var(--border-subtle)',
    padding: 'var(--space-xxl)',
    boxShadow: 'var(--shadow-2)'
  },
  photographic: {
    background: 'var(--surface-code)',
    borderColor: 'var(--border-code)',
    color: 'var(--text-on-code)',
    padding: 0,
    overflow: 'hidden'
  }
};
function Card({
  variant = 'base',
  as: Tag = 'div',
  padding,
  children,
  style,
  ...rest
}) {
  const v = VARIANTS[variant] || VARIANTS.base;
  return /*#__PURE__*/React.createElement(Tag, _extends({
    style: {
      display: 'block',
      minWidth: 0,
      color: 'var(--text-primary)',
      borderRadius: 'var(--radius-card)',
      borderStyle: 'solid',
      borderWidth: 'var(--border-width)',
      ...v,
      ...(padding != null ? {
        padding
      } : null),
      ...style
    }
  }, rest), children);
}
Object.assign(__ds_scope, { Card });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/cards/Card.jsx", error: String((e && e.message) || e) }); }

// components/content/TestimonialCard.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function TestimonialCard({
  quote,
  name,
  role,
  company,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("figure", _extends({
    style: {
      margin: 0,
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xl)',
      minWidth: 0,
      padding: 'var(--space-xxl)',
      borderRadius: 'var(--radius-card)',
      background: 'var(--surface-card)',
      border: 'var(--border-width) solid var(--border-subtle)',
      color: 'var(--text-primary)',
      ...style
    }
  }, rest), company && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-md-medium)',
      color: 'var(--text-tertiary)'
    }
  }, company), /*#__PURE__*/React.createElement("blockquote", {
    style: {
      margin: 0,
      font: 'var(--type-body-md)',
      color: 'var(--text-primary)',
      textWrap: 'pretty'
    }
  }, "\u201C", quote, "\u201D"), /*#__PURE__*/React.createElement("figcaption", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 2,
      marginTop: 'auto'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm-medium)',
      color: 'var(--text-primary)'
    }
  }, name), role && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)'
    }
  }, role)));
}
Object.assign(__ds_scope, { TestimonialCard });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/content/TestimonialCard.jsx", error: String((e && e.message) || e) }); }

// components/footer/FooterLink.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function FooterLink({
  href = '#',
  children,
  onClick,
  style,
  ...rest
}) {
  const [pressed, setPressed] = React.useState(false);
  return /*#__PURE__*/React.createElement("a", _extends({
    href: href,
    onClick: onClick,
    onPointerDown: () => setPressed(true),
    onPointerUp: () => setPressed(false),
    onPointerLeave: () => setPressed(false),
    style: {
      display: 'inline-block',
      padding: 'var(--space-xxs) 0',
      font: 'var(--type-body-sm)',
      color: 'var(--text-link)',
      textDecorationLine: pressed ? 'underline' : 'none',
      textDecorationThickness: 2,
      textUnderlineOffset: 3,
      ...style
    }
  }, rest), children);
}
Object.assign(__ds_scope, { FooterLink });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/footer/FooterLink.jsx", error: String((e && e.message) || e) }); }

// components/forms/ContactFormPanel.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function ContactFormPanel({
  title,
  description,
  onSubmit,
  maxWidth = 'var(--form-panel-max)',
  children,
  style,
  ...rest
}) {
  const Tag = onSubmit ? 'form' : 'div';
  return /*#__PURE__*/React.createElement(Tag, _extends({
    onSubmit: onSubmit ? e => {
      e.preventDefault();
      onSubmit(e);
    } : undefined,
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-md)',
      width: '100%',
      maxWidth,
      padding: 'var(--space-xxl)',
      borderRadius: 'var(--radius-card)',
      background: 'var(--surface-warm)',
      border: 'var(--border-width) solid var(--border-warm)',
      color: 'var(--text-on-warm)',
      ...style
    }
  }, rest), (title || description) && /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xs)',
      marginBottom: 'var(--space-xs)'
    }
  }, title && /*#__PURE__*/React.createElement("h2", {
    style: {
      margin: 0,
      font: 'var(--type-heading-4)'
    }
  }, title), description && /*#__PURE__*/React.createElement("p", {
    style: {
      margin: 0,
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)'
    }
  }, description)), children);
}
Object.assign(__ds_scope, { ContactFormPanel });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/ContactFormPanel.jsx", error: String((e && e.message) || e) }); }

// components/forms/TextArea.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function TextArea({
  label,
  helperText,
  id,
  rows = 5,
  disabled = false,
  required = false,
  style,
  inputStyle,
  onFocus,
  onBlur,
  ...rest
}) {
  const [focused, setFocused] = React.useState(false);
  const autoId = React.useId ? React.useId() : undefined;
  const inputId = id || autoId;
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xs)',
      minWidth: 0,
      ...style
    }
  }, label && /*#__PURE__*/React.createElement("label", {
    htmlFor: inputId,
    style: {
      font: 'var(--type-body-sm-medium)',
      color: 'var(--text-primary)'
    }
  }, label, required && /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      color: 'var(--text-accent)'
    }
  }, " *")), /*#__PURE__*/React.createElement("textarea", _extends({
    id: inputId,
    rows: rows,
    disabled: disabled,
    required: required,
    onFocus: e => {
      setFocused(true);
      onFocus && onFocus(e);
    },
    onBlur: e => {
      setFocused(false);
      onBlur && onBlur(e);
    },
    style: {
      width: '100%',
      padding: 'var(--space-md)',
      font: 'var(--type-body-md)',
      color: disabled ? 'var(--text-disabled)' : 'var(--text-primary)',
      background: disabled ? 'var(--surface-subtle)' : 'var(--surface-card)',
      border: 'var(--border-width) solid ' + (focused ? 'var(--border-accent)' : disabled ? 'var(--border-default)' : 'var(--border-strong)'),
      boxShadow: focused ? 'inset 0 0 0 1px var(--border-accent)' : 'none',
      borderRadius: 'var(--radius-input)',
      outline: 'none',
      resize: 'vertical',
      transition: 'border-color var(--duration-fast) var(--ease-standard), box-shadow var(--duration-fast) var(--ease-standard)',
      ...inputStyle
    }
  }, rest)), helperText && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-caption)',
      color: 'var(--text-tertiary)'
    }
  }, helperText));
}
Object.assign(__ds_scope, { TextArea });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/TextArea.jsx", error: String((e && e.message) || e) }); }

// components/icons/Icon.jsx
try { (() => {
// Lucide (ISC licence) glyphs, copied programmatically from lucide-static@1.53.0.
// Rendered with round caps/joins and a stroke that stays a constant 2px at any size,
// so icons match the system's 2px outlines.
const ICONS = {
  "sparkles": [["path", {
    "d": "M11.017 2.814a1 1 0 0 1 1.966 0l1.051 5.558a2 2 0 0 0 1.594 1.594l5.558 1.051a1 1 0 0 1 0 1.966l-5.558 1.051a2 2 0 0 0-1.594 1.594l-1.051 5.558a1 1 0 0 1-1.966 0l-1.051-5.558a2 2 0 0 0-1.594-1.594l-5.558-1.051a1 1 0 0 1 0-1.966l5.558-1.051a2 2 0 0 0 1.594-1.594z"
  }], ["path", {
    "d": "M20 2v4"
  }], ["path", {
    "d": "M22 4h-4"
  }], ["circle", {
    "cx": "4",
    "cy": "20",
    "r": "2"
  }]],
  "x": [["path", {
    "d": "M18 6 6 18"
  }], ["path", {
    "d": "m6 6 12 12"
  }]],
  "chevron-down": [["path", {
    "d": "m6 9 6 6 6-6"
  }]],
  "arrow-right": [["path", {
    "d": "M5 12h14"
  }], ["path", {
    "d": "m12 5 7 7-7 7"
  }]],
  "info": [["circle", {
    "cx": "12",
    "cy": "12",
    "r": "10"
  }], ["path", {
    "d": "M12 16v-4"
  }], ["path", {
    "d": "M12 8h.01"
  }]],
  "sun": [["circle", {
    "cx": "12",
    "cy": "12",
    "r": "4"
  }], ["path", {
    "d": "M12 2v2"
  }], ["path", {
    "d": "M12 20v2"
  }], ["path", {
    "d": "m4.93 4.93 1.41 1.41"
  }], ["path", {
    "d": "m17.66 17.66 1.41 1.41"
  }], ["path", {
    "d": "M2 12h2"
  }], ["path", {
    "d": "M20 12h2"
  }], ["path", {
    "d": "m6.34 17.66-1.41 1.41"
  }], ["path", {
    "d": "m19.07 4.93-1.41 1.41"
  }]],
  "plus": [["path", {
    "d": "M5 12h14"
  }], ["path", {
    "d": "M12 5v14"
  }]],
  "zap": [["path", {
    "d": "M15.914 4a1.5 1.5 0 00-2.474-1.561l-9 9A1.5 1.5 0 005.5 14h4.002a.5.5 0 01.471.666L8.086 20a1.5 1.5 0 002.475 1.56l9-9A1.5 1.5 0 0018.5 10h-3.997a.5.5 0 01-.472-.667z"
  }]],
  "map-pin": [["path", {
    "d": "M20 10c0 4.993-5.539 10.193-7.399 11.799a1 1 0 0 1-1.202 0C9.539 20.193 4 14.993 4 10a8 8 0 0 1 16 0"
  }], ["circle", {
    "cx": "12",
    "cy": "10",
    "r": "3"
  }]],
  "chevron-left": [["path", {
    "d": "m15 18-6-6 6-6"
  }]],
  "arrow-left": [["path", {
    "d": "m12 19-7-7 7-7"
  }], ["path", {
    "d": "M19 12H5"
  }]],
  "bell": [["path", {
    "d": "M10.268 21a2 2 0 0 0 3.464 0"
  }], ["path", {
    "d": "M3.262 15.326A1 1 0 0 0 4 17h16a1 1 0 0 0 .74-1.673C19.41 13.956 18 12.499 18 8A6 6 0 0 0 6 8c0 4.499-1.411 5.956-2.738 7.326"
  }]],
  "arrow-up-right": [["path", {
    "d": "M7 7h10v10"
  }], ["path", {
    "d": "M7 17 17 7"
  }]],
  "cloud": [["path", {
    "d": "M17.5 19H9a7 7 0 1 1 6.71-9h1.79a4.5 4.5 0 1 1 0 9Z"
  }]],
  "external-link": [["path", {
    "d": "M15 3h6v6"
  }], ["path", {
    "d": "M10 14 21 3"
  }], ["path", {
    "d": "M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6"
  }]],
  "smartphone": [["rect", {
    "width": "14",
    "height": "20",
    "x": "5",
    "y": "2",
    "rx": "2",
    "ry": "2"
  }], ["path", {
    "d": "M12 18h.01"
  }]],
  "share-2": [["circle", {
    "cx": "18",
    "cy": "5",
    "r": "3"
  }], ["circle", {
    "cx": "6",
    "cy": "12",
    "r": "3"
  }], ["circle", {
    "cx": "18",
    "cy": "19",
    "r": "3"
  }], ["line", {
    "x1": "8.59",
    "x2": "15.42",
    "y1": "13.51",
    "y2": "17.49"
  }], ["line", {
    "x1": "15.41",
    "x2": "8.59",
    "y1": "6.51",
    "y2": "10.49"
  }]],
  "moon": [["path", {
    "d": "M20.985 12.486a9 9 0 1 1-9.473-9.472c.405-.022.617.46.402.803a6 6 0 0 0 8.268 8.268c.344-.215.825-.004.803.401"
  }]],
  "mail": [["path", {
    "d": "m22 7-8.991 5.727a2 2 0 0 1-2.009 0L2 7"
  }], ["rect", {
    "x": "2",
    "y": "4",
    "width": "20",
    "height": "16",
    "rx": "2"
  }]],
  "send": [["path", {
    "d": "M14.536 21.686a.5.5 0 0 0 .937-.024l6.5-19a.496.496 0 0 0-.635-.635l-19 6.5a.5.5 0 0 0-.024.937l7.93 3.18a2 2 0 0 1 1.112 1.11z"
  }], ["path", {
    "d": "m21.854 2.147-10.94 10.939"
  }]],
  "book-open": [["path", {
    "d": "M12 5v16"
  }], ["path", {
    "d": "M20.001 19A2 2 0 0022 17V5a2 2 0 00-1.999-2L16 3.002A5 5 0 0012 5a5 5 0 00-4-2H4a2 2 0 00-2 2v12a2 2 0 001.999 2H8a5 5 0 014 2 5 5 0 014-2z"
  }]],
  "menu": [["path", {
    "d": "M4 5h16"
  }], ["path", {
    "d": "M4 12h16"
  }], ["path", {
    "d": "M4 19h16"
  }]],
  "chevron-right": [["path", {
    "d": "m9 18 6-6-6-6"
  }]],
  "apple": [["path", {
    "d": "M12 6.528V3a1 1 0 0 1 1-1h0"
  }], ["path", {
    "d": "M18.237 21A15 15 0 0 0 22 11a6 6 0 0 0-10-4.472A6 6 0 0 0 2 11a15.1 15.1 0 0 0 3.763 10 3 3 0 0 0 3.648.648 5.5 5.5 0 0 1 5.178 0A3 3 0 0 0 18.237 21"
  }]],
  "clock": [["circle", {
    "cx": "12",
    "cy": "12",
    "r": "10"
  }], ["path", {
    "d": "M12 6v6l4 2"
  }]],
  "bot": [["path", {
    "d": "M12 8V4H8"
  }], ["rect", {
    "width": "16",
    "height": "12",
    "x": "4",
    "y": "8",
    "rx": "2"
  }], ["path", {
    "d": "M2 14h2"
  }], ["path", {
    "d": "M20 14h2"
  }], ["path", {
    "d": "M15 13v2"
  }], ["path", {
    "d": "M9 13v2"
  }]],
  "download": [["path", {
    "d": "M12 15V3"
  }], ["path", {
    "d": "M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"
  }], ["path", {
    "d": "m7 10 5 5 5-5"
  }]],
  "languages": [["path", {
    "d": "m5 8 6 6"
  }], ["path", {
    "d": "m4 14 6-6 2-3"
  }], ["path", {
    "d": "M2 5h12"
  }], ["path", {
    "d": "M7 2h1"
  }], ["path", {
    "d": "m22 22-5-10-5 10"
  }], ["path", {
    "d": "M14 18h6"
  }]],
  "git-branch": [["path", {
    "d": "M15 6a9 9 0 0 0-9 9V3"
  }], ["circle", {
    "cx": "18",
    "cy": "6",
    "r": "3"
  }], ["circle", {
    "cx": "6",
    "cy": "18",
    "r": "3"
  }]],
  "phone": [["path", {
    "d": "M13.832 16.568a1 1 0 0 0 1.213-.303l.355-.465A2 2 0 0 1 17 15h3a2 2 0 0 1 2 2v3a2 2 0 0 1-2 2A18 18 0 0 1 2 4a2 2 0 0 1 2-2h3a2 2 0 0 1 2 2v3a2 2 0 0 1-.8 1.6l-.468.351a1 1 0 0 0-.292 1.233 14 14 0 0 0 6.392 6.384"
  }]],
  "handshake": [["path", {
    "d": "m11 17 2 2a1 1 0 1 0 3-3"
  }], ["path", {
    "d": "m14 14 2.5 2.5a1 1 0 1 0 3-3l-3.88-3.88a3 3 0 0 0-4.24 0l-.88.88a1 1 0 1 1-3-3l2.81-2.81a5.79 5.79 0 0 1 7.06-.87l.47.28a2 2 0 0 0 1.42.25L21 4"
  }], ["path", {
    "d": "m21 3 1 11h-2"
  }], ["path", {
    "d": "M3 3 2 14l6.5 6.5a1 1 0 1 0 3-3"
  }], ["path", {
    "d": "M3 4h8"
  }]],
  "cpu": [["path", {
    "d": "M12 20v2"
  }], ["path", {
    "d": "M12 2v2"
  }], ["path", {
    "d": "M17 20v2"
  }], ["path", {
    "d": "M17 2v2"
  }], ["path", {
    "d": "M2 12h2"
  }], ["path", {
    "d": "M2 17h2"
  }], ["path", {
    "d": "M2 7h2"
  }], ["path", {
    "d": "M20 12h2"
  }], ["path", {
    "d": "M20 17h2"
  }], ["path", {
    "d": "M20 7h2"
  }], ["path", {
    "d": "M7 20v2"
  }], ["path", {
    "d": "M7 2v2"
  }], ["rect", {
    "x": "4",
    "y": "4",
    "width": "16",
    "height": "16",
    "rx": "2"
  }], ["rect", {
    "x": "8",
    "y": "8",
    "width": "8",
    "height": "8",
    "rx": "1"
  }]],
  "key-round": [["path", {
    "d": "M2.586 17.414A2 2 0 0 0 2 18.828V21a1 1 0 0 0 1 1h3a1 1 0 0 0 1-1v-1a1 1 0 0 1 1-1h1a1 1 0 0 0 1-1v-1a1 1 0 0 1 1-1h.172a2 2 0 0 0 1.414-.586l.814-.814a6.5 6.5 0 1 0-4-4z"
  }], ["circle", {
    "cx": "16.5",
    "cy": "7.5",
    "r": ".5",
    "fill": "currentColor"
  }]],
  "rss": [["path", {
    "d": "M4 11a9 9 0 0 1 9 9"
  }], ["path", {
    "d": "M4 4a16 16 0 0 1 16 16"
  }], ["circle", {
    "cx": "5",
    "cy": "19",
    "r": "1"
  }]],
  "calendar-days": [["path", {
    "d": "M8 2v3"
  }], ["path", {
    "d": "M16 2v3"
  }], ["rect", {
    "x": "3",
    "y": "3",
    "width": "18",
    "height": "18",
    "rx": "2"
  }], ["path", {
    "d": "M3 9h18"
  }], ["path", {
    "d": "M8 13h.01"
  }], ["path", {
    "d": "M12 13h.01"
  }], ["path", {
    "d": "M16 13h.01"
  }], ["path", {
    "d": "M8 17h.01"
  }], ["path", {
    "d": "M12 17h.01"
  }], ["path", {
    "d": "M16 17h.01"
  }]],
  "square-terminal": [["path", {
    "d": "m7 11 2-2-2-2"
  }], ["path", {
    "d": "M11 13h4"
  }], ["rect", {
    "width": "18",
    "height": "18",
    "x": "3",
    "y": "3",
    "rx": "2",
    "ry": "2"
  }]],
  "circle-check": [["circle", {
    "cx": "12",
    "cy": "12",
    "r": "10"
  }], ["path", {
    "d": "m16 9-5.5 5.5L8 12"
  }]],
  "message-square": [["path", {
    "d": "M22 17a2 2 0 0 1-2 2H6.828a2 2 0 0 0-1.414.586l-2.202 2.202A.71.71 0 0 1 2 21.286V5a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2z"
  }]],
  "settings": [["path", {
    "d": "M9.671 4.136a2.34 2.34 0 0 1 4.659 0 2.34 2.34 0 0 0 3.319 1.915 2.34 2.34 0 0 1 2.33 4.033 2.34 2.34 0 0 0 0 3.831 2.34 2.34 0 0 1-2.33 4.033 2.34 2.34 0 0 0-3.319 1.915 2.34 2.34 0 0 1-4.659 0 2.34 2.34 0 0 0-3.32-1.915 2.34 2.34 0 0 1-2.33-4.033 2.34 2.34 0 0 0 0-3.831A2.34 2.34 0 0 1 6.35 6.051a2.34 2.34 0 0 0 3.319-1.915"
  }], ["circle", {
    "cx": "12",
    "cy": "12",
    "r": "3"
  }]],
  "bookmark": [["path", {
    "d": "M17 3a2 2 0 0 1 2 2v15a1 1 0 0 1-1.496.868l-4.512-2.578a2 2 0 0 0-1.984 0l-4.512 2.578A1 1 0 0 1 5 20V5a2 2 0 0 1 2-2z"
  }]],
  "terminal": [["path", {
    "d": "M12 19h8"
  }], ["path", {
    "d": "m4 17 6-6-6-6"
  }]],
  "truck": [["path", {
    "d": "M14 18V6a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v11a1 1 0 0 0 1 1h2"
  }], ["path", {
    "d": "M15 18H9"
  }], ["path", {
    "d": "M19 18h2a1 1 0 0 0 1-1v-3.65a1 1 0 0 0-.22-.624l-3.48-4.35A1 1 0 0 0 17.52 8H14"
  }], ["circle", {
    "cx": "17",
    "cy": "18",
    "r": "2"
  }], ["circle", {
    "cx": "7",
    "cy": "18",
    "r": "2"
  }]],
  "shield-check": [["path", {
    "d": "M20 13c0 5-3.5 7.5-7.66 8.95a1 1 0 0 1-.67-.01C7.5 20.5 4 18 4 13V6a1 1 0 0 1 1-1c2 0 4.5-1.2 6.24-2.72a1.17 1.17 0 0 1 1.52 0C14.51 3.81 17 5 19 5a1 1 0 0 1 1 1z"
  }], ["path", {
    "d": "m9 12 2 2 4-4"
  }]],
  "shopping-bag": [["path", {
    "d": "M16 10a4 4 0 0 1-8 0"
  }], ["path", {
    "d": "M3.103 6.034h17.794"
  }], ["path", {
    "d": "M3.4 5.467a2 2 0 0 0-.4 1.2V20a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6.667a2 2 0 0 0-.4-1.2l-2-2.667A2 2 0 0 0 17 2H7a2 2 0 0 0-1.6.8z"
  }]],
  "play": [["path", {
    "d": "M5 5a2 2 0 0 1 3.008-1.728l11.997 6.998a2 2 0 0 1 .003 3.458l-12 7A2 2 0 0 1 5 19z"
  }]],
  "star": [["path", {
    "d": "M11.525 2.295a.53.53 0 0 1 .95 0l2.31 4.679a2.123 2.123 0 0 0 1.595 1.16l5.166.756a.53.53 0 0 1 .294.904l-3.736 3.638a2.123 2.123 0 0 0-.611 1.878l.882 5.14a.53.53 0 0 1-.771.56l-4.618-2.428a2.122 2.122 0 0 0-1.973 0L6.396 21.01a.53.53 0 0 1-.77-.56l.881-5.139a2.122 2.122 0 0 0-.611-1.879L2.16 9.795a.53.53 0 0 1 .294-.906l5.165-.755a2.122 2.122 0 0 0 1.597-1.16z"
  }]],
  "box": [["path", {
    "d": "M21 8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16Z"
  }], ["path", {
    "d": "m3.3 7 8.7 5 8.7-5"
  }], ["path", {
    "d": "M12 22V12"
  }]],
  "graduation-cap": [["path", {
    "d": "M21.42 10.922a1 1 0 0 0-.019-1.838L12.83 5.18a2 2 0 0 0-1.66 0L2.6 9.08a1 1 0 0 0 0 1.832l8.57 3.908a2 2 0 0 0 1.66 0z"
  }], ["path", {
    "d": "M22 10v6"
  }], ["path", {
    "d": "M6 12.5V16a6 3 0 0 0 12 0v-3.5"
  }]],
  "circle-alert": [["circle", {
    "cx": "12",
    "cy": "12",
    "r": "10"
  }], ["line", {
    "x1": "12",
    "x2": "12",
    "y1": "8",
    "y2": "12"
  }], ["line", {
    "x1": "12",
    "x2": "12.01",
    "y1": "16",
    "y2": "16"
  }]],
  "database": [["ellipse", {
    "cx": "12",
    "cy": "5",
    "rx": "9",
    "ry": "3"
  }], ["path", {
    "d": "M3 5V19A9 3 0 0 0 21 19V5"
  }], ["path", {
    "d": "M3 12A9 3 0 0 0 21 12"
  }]],
  "gauge": [["path", {
    "d": "m12 14 4-4"
  }], ["path", {
    "d": "M3.34 19a10 10 0 1 1 17.32 0"
  }]],
  "check": [["path", {
    "d": "M20 6 9 17l-5-5"
  }]],
  "braces": [["path", {
    "d": "M8 3H7a2 2 0 0 0-2 2v5a2 2 0 0 1-2 2 2 2 0 0 1 2 2v5c0 1.1.9 2 2 2h1"
  }], ["path", {
    "d": "M16 21h1a2 2 0 0 0 2-2v-5c0-1.1.9-2 2-2a2 2 0 0 1-2-2V5a2 2 0 0 0-2-2h-1"
  }]],
  "file-text": [["path", {
    "d": "M6 22a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h8a2.4 2.4 0 0 1 1.704.706l3.588 3.588A2.4 2.4 0 0 1 20 8v12a2 2 0 0 1-2 2z"
  }], ["path", {
    "d": "M14 2v5a1 1 0 0 0 1 1h5"
  }], ["path", {
    "d": "M10 9H8"
  }], ["path", {
    "d": "M16 13H8"
  }], ["path", {
    "d": "M16 17H8"
  }]],
  "globe": [["circle", {
    "cx": "12",
    "cy": "12",
    "r": "10"
  }], ["path", {
    "d": "M12 2a14.5 14.5 0 0 0 0 20 14.5 14.5 0 0 0 0-20"
  }], ["path", {
    "d": "M2 12h20"
  }]],
  "copy": [["rect", {
    "width": "14",
    "height": "14",
    "x": "8",
    "y": "8",
    "rx": "2",
    "ry": "2"
  }], ["path", {
    "d": "M4 16c-1.1 0-2-.9-2-2V4c0-1.1.9-2 2-2h10c1.1 0 2 .9 2 2"
  }]],
  "workflow": [["rect", {
    "width": "8",
    "height": "8",
    "x": "3",
    "y": "3",
    "rx": "2"
  }], ["path", {
    "d": "M7 11v4a2 2 0 0 0 2 2h4"
  }], ["rect", {
    "width": "8",
    "height": "8",
    "x": "13",
    "y": "13",
    "rx": "2"
  }]],
  "newspaper": [["path", {
    "d": "M15 18h-5"
  }], ["path", {
    "d": "M18 14h-8"
  }], ["path", {
    "d": "M4 22h16a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H8a2 2 0 0 0-2 2v16a2 2 0 0 1-4 0v-9a2 2 0 0 1 2-2h2"
  }], ["rect", {
    "width": "8",
    "height": "4",
    "x": "10",
    "y": "6",
    "rx": "1"
  }]],
  "rocket": [["path", {
    "d": "M12 15v5s3.03-.55 4-2c1.08-1.62 0-5 0-5"
  }], ["path", {
    "d": "M4.5 16.5c-1.5 1.26-2 5-2 5s3.74-.5 5-2c.71-.84.7-2.13-.09-2.91a2.18 2.18 0 0 0-2.91-.09"
  }], ["path", {
    "d": "M9 12a22 22 0 0 1 2-3.95A12.88 12.88 0 0 1 22 2c0 2.72-.78 7.5-6 11a22.4 22.4 0 0 1-4 2z"
  }], ["path", {
    "d": "M9 12H4s.55-3.03 2-4c1.62-1.08 5 .05 5 .05"
  }]],
  "badge-check": [["path", {
    "d": "M3.85 8.62a4 4 0 0 1 4.78-4.77 4 4 0 0 1 6.74 0 4 4 0 0 1 4.78 4.78 4 4 0 0 1 0 6.74 4 4 0 0 1-4.77 4.78 4 4 0 0 1-6.75 0 4 4 0 0 1-4.78-4.77 4 4 0 0 1 0-6.76Z"
  }], ["path", {
    "d": "m16 9-5.5 5.5L8 12"
  }]],
  "calendar": [["path", {
    "d": "M8 2v3"
  }], ["path", {
    "d": "M16 2v3"
  }], ["rect", {
    "x": "3",
    "y": "3",
    "width": "18",
    "height": "18",
    "rx": "2"
  }], ["path", {
    "d": "M3 9h18"
  }]],
  "minus": [["path", {
    "d": "M5 12h14"
  }]],
  "quote": [["path", {
    "d": "M16 3a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2 1 1 0 0 1 1 1v1a2 2 0 0 1-2 2 1 1 0 0 0-1 1v2a1 1 0 0 0 1 1 6 6 0 0 0 6-6V5a2 2 0 0 0-2-2z"
  }], ["path", {
    "d": "M5 3a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2 1 1 0 0 1 1 1v1a2 2 0 0 1-2 2 1 1 0 0 0-1 1v2a1 1 0 0 0 1 1 6 6 0 0 0 6-6V5a2 2 0 0 0-2-2z"
  }]],
  "factory": [["path", {
    "d": "M12 16h.01"
  }], ["path", {
    "d": "M16 16h.01"
  }], ["path", {
    "d": "M3 19a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V8.5a.5.5 0 0 0-.769-.422l-4.462 2.844A.5.5 0 0 1 15 10.5v-2a.5.5 0 0 0-.769-.422L9.77 10.922A.5.5 0 0 1 9 10.5V5a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2z"
  }], ["path", {
    "d": "M8 16h.01"
  }]],
  "heart-pulse": [["path", {
    "d": "M2 9.5a5.5 5.5 0 0 1 9.591-3.676.56.56 0 0 0 .818 0A5.49 5.49 0 0 1 22 9.5c0 2.29-1.5 4-3 5.5l-5.492 5.313a2 2 0 0 1-3 .019L5 15c-1.5-1.5-3-3.2-3-5.5"
  }], ["path", {
    "d": "M3.22 13H9.5l.5-1 2 4.5 2-7 1.5 3.5h5.27"
  }]],
  "git-pull-request": [["circle", {
    "cx": "18",
    "cy": "18",
    "r": "3"
  }], ["circle", {
    "cx": "6",
    "cy": "6",
    "r": "3"
  }], ["path", {
    "d": "M13 6h3a2 2 0 0 1 2 2v7"
  }], ["line", {
    "x1": "6",
    "x2": "6",
    "y1": "9",
    "y2": "21"
  }]],
  "lock": [["rect", {
    "width": "18",
    "height": "11",
    "x": "3",
    "y": "11",
    "rx": "2",
    "ry": "2"
  }], ["path", {
    "d": "M7 11V7a5 5 0 0 1 10 0v4"
  }]],
  "building-2": [["path", {
    "d": "M10 12h4"
  }], ["path", {
    "d": "M10 8h4"
  }], ["path", {
    "d": "M14 21v-3a2 2 0 0 0-4 0v3"
  }], ["path", {
    "d": "M6 10H4a2 2 0 0 0-2 2v7a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2h-2"
  }], ["path", {
    "d": "M6 21V5a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v16"
  }]],
  "monitor-smartphone": [["path", {
    "d": "M18 8V6a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v7a2 2 0 0 0 2 2h8"
  }], ["path", {
    "d": "M10 19v-3.96 3.15"
  }], ["path", {
    "d": "M7 19h5"
  }], ["rect", {
    "width": "6",
    "height": "10",
    "x": "16",
    "y": "12",
    "rx": "2"
  }]],
  "landmark": [["path", {
    "d": "M10 18v-7"
  }], ["path", {
    "d": "M11.119 2.205a2 2 0 0 1 1.762 0l7.84 3.846A.5.5 0 0 1 20.5 7h-17a.5.5 0 0 1-.22-.949z"
  }], ["path", {
    "d": "M14 18v-7"
  }], ["path", {
    "d": "M18 18v-7"
  }], ["path", {
    "d": "M3 22h18"
  }], ["path", {
    "d": "M6 18v-7"
  }]],
  "timer": [["line", {
    "x1": "10",
    "x2": "14",
    "y1": "2",
    "y2": "2"
  }], ["line", {
    "x1": "12",
    "x2": "15",
    "y1": "14",
    "y2": "11"
  }], ["circle", {
    "cx": "12",
    "cy": "14",
    "r": "8"
  }]],
  "tablet-smartphone": [["rect", {
    "width": "10",
    "height": "14",
    "x": "3",
    "y": "8",
    "rx": "2"
  }], ["path", {
    "d": "M5 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v16a2 2 0 0 1-2 2h-2.4"
  }], ["path", {
    "d": "M8 18h.01"
  }]],
  "user": [["path", {
    "d": "M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"
  }], ["circle", {
    "cx": "12",
    "cy": "7",
    "r": "4"
  }]],
  "headphones": [["path", {
    "d": "M3 14h3a2 2 0 0 1 2 2v3a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-7a9 9 0 0 1 18 0v7a2 2 0 0 1-2 2h-1a2 2 0 0 1-2-2v-3a2 2 0 0 1 2-2h3"
  }]],
  "users": [["path", {
    "d": "M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"
  }], ["path", {
    "d": "M16 3.128a4 4 0 0 1 0 7.744"
  }], ["path", {
    "d": "M22 21v-2a4 4 0 0 0-3-3.87"
  }], ["circle", {
    "cx": "9",
    "cy": "7",
    "r": "4"
  }]],
  "life-buoy": [["circle", {
    "cx": "12",
    "cy": "12",
    "r": "10"
  }], ["path", {
    "d": "m4.93 4.93 4.24 4.24"
  }], ["path", {
    "d": "m14.83 9.17 4.24-4.24"
  }], ["path", {
    "d": "m14.83 14.83 4.24 4.24"
  }], ["path", {
    "d": "m9.17 14.83-4.24 4.24"
  }], ["circle", {
    "cx": "12",
    "cy": "12",
    "r": "4"
  }]],
  "chart-column": [["path", {
    "d": "M3 3v16a2 2 0 0 0 2 2h16"
  }], ["path", {
    "d": "M18 17V9"
  }], ["path", {
    "d": "M13 17V5"
  }], ["path", {
    "d": "M8 17v-3"
  }]],
  "code": [["path", {
    "d": "m16 18 6-6-6-6"
  }], ["path", {
    "d": "m8 6-6 6 6 6"
  }]],
  "circle-play": [["path", {
    "d": "M9 9.003a1 1 0 0 1 1.517-.859l4.997 2.997a1 1 0 0 1 0 1.718l-4.997 2.997A1 1 0 0 1 9 14.996z"
  }], ["circle", {
    "cx": "12",
    "cy": "12",
    "r": "10"
  }]],
  "layers": [["path", {
    "d": "M12.83 2.18a2 2 0 0 0-1.66 0L2.6 6.08a1 1 0 0 0 0 1.83l8.58 3.91a2 2 0 0 0 1.66 0l8.58-3.9a1 1 0 0 0 0-1.83z"
  }], ["path", {
    "d": "M2 12a1 1 0 0 0 .58.91l8.6 3.91a2 2 0 0 0 1.65 0l8.58-3.9A1 1 0 0 0 22 12"
  }], ["path", {
    "d": "M2 17a1 1 0 0 0 .58.91l8.6 3.91a2 2 0 0 0 1.65 0l8.58-3.9A1 1 0 0 0 22 17"
  }]],
  "search": [["path", {
    "d": "m21 21-4.34-4.34"
  }], ["circle", {
    "cx": "11",
    "cy": "11",
    "r": "8"
  }]],
  "pause": [["rect", {
    "x": "14",
    "y": "3",
    "width": "5",
    "height": "18",
    "rx": "1"
  }], ["rect", {
    "x": "5",
    "y": "3",
    "width": "5",
    "height": "18",
    "rx": "1"
  }]],
  "rotate-ccw": [["path", {
    "d": "M3 12a9 9 0 1 0 9-9 9.75 9.75 0 0 0-6.74 2.74L3 8"
  }], ["path", {
    "d": "M3 3v5h5"
  }]],
  "lightbulb": [["path", {
    "d": "M15 14c.2-1 .7-1.7 1.5-2.5 1-.9 1.5-2.2 1.5-3.5A6 6 0 0 0 6 8c0 1 .2 2.2 1.5 3.5.7.7 1.3 1.5 1.5 2.5"
  }], ["path", {
    "d": "M9 18h6"
  }], ["path", {
    "d": "M10 22h4"
  }]],
  "shopping-cart": [["path", {
    "d": "m2.05 2.05 1.099-.028a1 1 0 0 1 1.008.815l2.69 14.347A1 1 0 0 0 7.83 18H18"
  }], ["path", {
    "d": "M4.563 5h16.435a1 1 0 0 1 .981 1.204l-1.026 6.226A2 2 0 0 1 18.962 14H6.25"
  }], ["circle", {
    "cx": "18",
    "cy": "20",
    "r": "2"
  }], ["circle", {
    "cx": "8",
    "cy": "20",
    "r": "2"
  }]],
  "shopping-basket": [["path", {
    "d": "m15 11-1 9"
  }], ["path", {
    "d": "m19 11-4-7"
  }], ["path", {
    "d": "M2 11h20"
  }], ["path", {
    "d": "m3.5 11 1.6 7.4a2 2 0 0 0 2 1.6h9.8a2 2 0 0 0 2-1.6l1.7-7.4"
  }], ["path", {
    "d": "M4.5 15.5h15"
  }], ["path", {
    "d": "m5 11 4-7"
  }], ["path", {
    "d": "m9 11 1 9"
  }]],
  "trash-2": [["path", {
    "d": "M10 11v6"
  }], ["path", {
    "d": "M14 11v6"
  }], ["path", {
    "d": "M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6"
  }], ["path", {
    "d": "M3 6h18"
  }], ["path", {
    "d": "M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"
  }]],
  "list-checks": [["path", {
    "d": "M13 5h8"
  }], ["path", {
    "d": "M13 12h8"
  }], ["path", {
    "d": "M13 19h8"
  }], ["path", {
    "d": "m3 17 2 2 4-4"
  }], ["path", {
    "d": "m3 7 2 2 4-4"
  }]],
  "chef-hat": [["path", {
    "d": "M17 21a1 1 0 0 0 1-1v-5.35c0-.457.316-.844.727-1.041a4 4 0 0 0-2.134-7.589 5 5 0 0 0-9.186 0 4 4 0 0 0-2.134 7.588c.411.198.727.585.727 1.041V20a1 1 0 0 0 1 1Z"
  }], ["path", {
    "d": "M6 17h12"
  }]],
  "utensils": [["path", {
    "d": "M3 2v7c0 1.1.9 2 2 2h4a2 2 0 0 0 2-2V2"
  }], ["path", {
    "d": "M7 2v20"
  }], ["path", {
    "d": "M21 15V2a5 5 0 0 0-5 5v6c0 1.1.9 2 2 2h3Zm0 0v7"
  }]],
  "flame": [["path", {
    "d": "M12 3q1 4 4 6.5t3 5.5a1 1 0 0 1-14 0 5 5 0 0 1 1-3 1 1 0 0 0 5 0c0-2-1.5-3-1.5-5q0-2 2.5-4"
  }]],
  "carrot": [["path", {
    "d": "M15 16a1 1 0 0 0-7-7q-4 4-5.987 12.385a.5.5 0 0 0 .602.602Q11 20 15 16l-3-3"
  }], ["path", {
    "d": "M15 9q4 4 7 0-3-4-7 0 4-4 0-7-4 3 0 7"
  }], ["path", {
    "d": "m8 15-2.58-2.58"
  }]],
  "egg": [["path", {
    "d": "M12 2C8 2 4 8 4 14a8 8 0 0 0 16 0c0-6-4-12-8-12"
  }]]
};
const ICON_NAMES = Object.keys(ICONS);
function Icon({
  name,
  size = 20,
  stroke = 2,
  color = 'currentColor',
  title,
  style,
  ...rest
}) {
  const nodes = ICONS[name];
  if (!nodes) return null;
  const strokeWidth = stroke * 24 / size;
  return React.createElement('svg', {
    xmlns: 'http://www.w3.org/2000/svg',
    width: size,
    height: size,
    viewBox: '0 0 24 24',
    fill: 'none',
    stroke: color,
    strokeWidth,
    strokeLinecap: 'round',
    strokeLinejoin: 'round',
    role: title ? 'img' : undefined,
    'aria-label': title,
    'aria-hidden': title ? undefined : true,
    style: {
      display: 'block',
      flexShrink: 0,
      ...style
    },
    ...rest
  }, title ? React.createElement('title', null, title) : null, nodes.map(([tag, attrs], i) => React.createElement(tag, {
    key: i,
    ...attrs
  })));
}
Object.assign(__ds_scope, { ICON_NAMES, Icon });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/icons/Icon.jsx", error: String((e && e.message) || e) }); }

// components/badges/Badge.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
const VARIANTS = {
  primary: {
    background: 'var(--accent)',
    color: 'var(--accent-fg)'
  },
  cream: {
    background: 'var(--surface-warm-strong)',
    color: 'var(--text-primary)'
  },
  dark: {
    background: 'var(--surface-inverse)',
    color: 'var(--text-inverse)'
  }
};
function Badge({
  variant = 'cream',
  icon,
  children,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("span", _extends({
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 6,
      padding: '4px 10px',
      borderRadius: 'var(--radius-badge)',
      font: 'var(--type-caption-bold)',
      whiteSpace: 'nowrap',
      ...(VARIANTS[variant] || VARIANTS.cream),
      ...style
    }
  }, rest), icon && (typeof icon === 'string' ? /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: icon,
    size: 14
  }) : icon), children);
}
Object.assign(__ds_scope, { Badge });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/badges/Badge.jsx", error: String((e && e.message) || e) }); }

// components/badges/PromoBanner.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function PromoBanner({
  children,
  ctaLabel,
  ctaHref = '#',
  onCtaClick,
  onDismiss,
  sticky = false,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("div", _extends({
    role: "region",
    "aria-label": "Announcement",
    style: {
      position: sticky ? 'sticky' : 'relative',
      top: sticky ? 0 : undefined,
      zIndex: sticky ? 30 : undefined,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      gap: 'var(--space-sm)',
      padding: onDismiss ? 'var(--space-sm) 48px' : 'var(--space-sm) var(--space-md)',
      background: 'var(--surface-promo)',
      color: 'var(--text-on-promo)',
      borderBottom: 'var(--border-width) solid var(--border-promo)',
      font: 'var(--type-body-sm-medium)',
      textAlign: 'center',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("span", {
    style: {
      overflow: 'hidden',
      textOverflow: 'ellipsis',
      whiteSpace: 'nowrap',
      minWidth: 0
    }
  }, children), ctaLabel && /*#__PURE__*/React.createElement("a", {
    href: ctaHref,
    onClick: onCtaClick,
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 4,
      color: 'var(--accent)',
      textDecoration: 'underline',
      textDecorationThickness: 2,
      textUnderlineOffset: 3,
      whiteSpace: 'nowrap'
    }
  }, ctaLabel, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "arrow-right",
    size: 14
  })), onDismiss && /*#__PURE__*/React.createElement("button", {
    type: "button",
    "aria-label": "Dismiss announcement",
    onClick: onDismiss,
    style: {
      position: 'absolute',
      right: 12,
      top: '50%',
      transform: 'translateY(-50%)',
      display: 'grid',
      placeItems: 'center',
      width: 28,
      height: 28,
      padding: 0,
      border: 0,
      borderRadius: 'var(--radius-sm)',
      background: 'transparent',
      color: 'inherit',
      cursor: 'pointer'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "x",
    size: 16
  })));
}
Object.assign(__ds_scope, { PromoBanner });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/badges/PromoBanner.jsx", error: String((e && e.message) || e) }); }

// components/buttons/AppDownloadBadge.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
const PLATFORM = {
  ios: {
    icon: 'smartphone',
    eyebrow: 'Download for',
    label: 'iOS'
  },
  android: {
    icon: 'tablet-smartphone',
    eyebrow: 'Get it for',
    label: 'Android'
  },
  desktop: {
    icon: 'monitor-smartphone',
    eyebrow: 'Download for',
    label: 'Desktop'
  }
};
function AppDownloadBadge({
  platform = 'ios',
  eyebrow,
  label,
  href = '#',
  style,
  ...rest
}) {
  const p = PLATFORM[platform] || PLATFORM.ios;
  return /*#__PURE__*/React.createElement("a", _extends({
    href: href,
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 10,
      padding: 'var(--space-sm) var(--space-md)',
      borderRadius: 'var(--radius-md)',
      background: 'var(--surface-inverse)',
      color: 'var(--text-inverse)',
      border: 'var(--border-width) solid var(--surface-inverse)',
      textDecoration: 'none',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: p.icon,
    size: 22
  }), /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 1
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-micro)'
    }
  }, eyebrow || p.eyebrow), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-caption-bold)',
      fontSize: 15
    }
  }, label || p.label)));
}
Object.assign(__ds_scope, { AppDownloadBadge });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/buttons/AppDownloadBadge.jsx", error: String((e && e.message) || e) }); }

// components/buttons/Button.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
const VARIANTS = {
  primary: {
    bg: 'var(--accent)',
    fg: 'var(--accent-fg)',
    border: 'var(--accent)',
    pBg: 'var(--accent-pressed)',
    pBorder: 'var(--accent-pressed)'
  },
  dark: {
    bg: 'var(--surface-inverse)',
    fg: 'var(--text-inverse)',
    border: 'var(--surface-inverse)',
    pBg: 'var(--inverse-pressed)',
    pBorder: 'var(--inverse-pressed)'
  },
  secondary: {
    bg: 'transparent',
    fg: 'var(--text-primary)',
    border: 'var(--border-strong)',
    pBg: 'var(--surface-subtle)'
  },
  cream: {
    bg: 'var(--surface-warm)',
    fg: 'var(--text-on-warm)',
    border: 'var(--border-warm)',
    pBg: 'var(--surface-warm-strong)'
  },
  'on-cream': {
    bg: 'var(--surface-card)',
    fg: 'var(--text-primary)',
    border: 'var(--border-warm)',
    pBg: 'var(--surface-warm)'
  }
};
function renderIcon(icon, size) {
  if (!icon) return null;
  return typeof icon === 'string' ? /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: icon,
    size: size
  }) : icon;
}
function Button({
  variant = 'primary',
  iconLeft,
  iconRight,
  fullWidth = false,
  disabled = false,
  href,
  type = 'button',
  onClick,
  children,
  style,
  ...rest
}) {
  const [pressed, setPressed] = React.useState(false);
  const isLink = variant === 'link';
  const v = VARIANTS[variant] || VARIANTS.primary;
  const press = {
    onPointerDown: () => !disabled && setPressed(true),
    onPointerUp: () => setPressed(false),
    onPointerLeave: () => setPressed(false),
    onPointerCancel: () => setPressed(false)
  };
  const base = {
    display: fullWidth ? 'flex' : 'inline-flex',
    width: fullWidth ? '100%' : undefined,
    alignItems: 'center',
    justifyContent: 'center',
    gap: isLink ? 6 : 8,
    cursor: disabled ? 'not-allowed' : 'pointer',
    textDecoration: 'none',
    whiteSpace: 'nowrap',
    userSelect: 'none',
    WebkitTapHighlightColor: 'transparent',
    transition: 'background-color var(--duration-fast) var(--ease-standard), border-color var(--duration-fast) var(--ease-standard), color var(--duration-fast) var(--ease-standard)'
  };
  const look = isLink ? {
    background: 'transparent',
    border: 0,
    padding: 0,
    font: 'var(--type-body-sm-medium)',
    color: disabled ? 'var(--text-disabled)' : 'var(--text-link)',
    textDecorationLine: pressed ? 'underline' : 'none',
    textDecorationThickness: 2,
    textUnderlineOffset: 3
  } : {
    padding: '10px 20px',
    font: 'var(--type-button-md)',
    borderRadius: 'var(--radius-button)',
    borderStyle: 'solid',
    borderWidth: 'var(--border-width)',
    background: disabled ? 'var(--disabled-bg)' : pressed ? v.pBg : v.bg,
    borderColor: disabled ? 'var(--disabled-bg)' : pressed && v.pBorder ? v.pBorder : v.border,
    color: disabled ? 'var(--disabled-fg)' : v.fg
  };
  const content = /*#__PURE__*/React.createElement(React.Fragment, null, renderIcon(iconLeft, 16), children != null && /*#__PURE__*/React.createElement("span", null, children), renderIcon(iconRight, 16));
  const shared = {
    ...press,
    style: {
      ...base,
      ...look,
      ...style
    },
    ...rest
  };
  if (href && !disabled) {
    return /*#__PURE__*/React.createElement("a", _extends({
      href: href,
      onClick: onClick
    }, shared), content);
  }
  return /*#__PURE__*/React.createElement("button", _extends({
    type: type,
    disabled: disabled,
    onClick: onClick
  }, shared), content);
}
Object.assign(__ds_scope, { Button });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/buttons/Button.jsx", error: String((e && e.message) || e) }); }

// components/code/CodeBlock.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
const KEYWORDS = new Set(['const', 'let', 'var', 'function', 'return', 'import', 'from', 'export', 'default', 'async', 'await', 'if', 'else', 'for', 'while', 'class', 'new', 'type', 'interface', 'extends', 'def', 'in', 'of', 'try', 'catch', 'throw', 'yield', 'with', 'as', 'true', 'false', 'null', 'undefined', 'None', 'True', 'False', 'self', 'this']);
const HASH_LANGS = new Set(['python', 'py', 'bash', 'sh', 'shell', 'yaml', 'yml', 'toml', 'ruby', 'rb']);
const COLORS = {
  comment: 'var(--code-comment)',
  string: 'var(--code-string)',
  number: 'var(--code-number)',
  keyword: 'var(--code-keyword)',
  fn: 'var(--code-function)',
  type: 'var(--code-number)',
  punct: 'var(--code-punctuation)',
  plain: 'var(--code-property)'
};
function tokenize(line, hash) {
  const re = hash ? /(#.*$)|("(?:[^"\\]|\\.)*"|'(?:[^'\\]|\\.)*')|(\b\d+(?:\.\d+)?\b)|([A-Za-z_$][\w$]*)|(\s+)|([^\w\s])/g : /(\/\/.*$)|("(?:[^"\\]|\\.)*"|'(?:[^'\\]|\\.)*'|`(?:[^`\\]|\\.)*`)|(\b\d+(?:\.\d+)?\b)|([A-Za-z_$][\w$]*)|(\s+)|([^\w\s])/g;
  const out = [];
  let m;
  while (m = re.exec(line)) {
    const [t, c, s, n, id, ws, p] = m;
    let k = 'plain';
    if (c) k = 'comment';else if (s) k = 'string';else if (n) k = 'number';else if (id) k = KEYWORDS.has(id) ? 'keyword' : /^\s*\(/.test(line.slice(re.lastIndex)) ? 'fn' : /^[A-Z]/.test(id) ? 'type' : 'plain';else if (p) k = 'punct';else if (ws) k = 'ws';
    out.push([k, t]);
  }
  return out;
}
function CodeBlock({
  code = '',
  filename,
  language,
  variant = 'block',
  showHeader,
  copyable = true,
  lineNumbers = false,
  highlight = true,
  maxHeight,
  style,
  ...rest
}) {
  const [copied, setCopied] = React.useState(false);
  const lines = String(code).replace(/\n$/, '').split('\n');
  const hash = HASH_LANGS.has(String(language || '').toLowerCase());
  const header = showHeader ?? Boolean(filename || language || copyable);
  const copy = () => {
    try {
      navigator.clipboard && navigator.clipboard.writeText(code);
    } catch (e) {}
    setCopied(true);
    setTimeout(() => setCopied(false), 1500);
  };
  const gutter = String(lines.length).length;
  return /*#__PURE__*/React.createElement("div", _extends({
    style: {
      minWidth: 0,
      background: 'var(--surface-code)',
      color: 'var(--text-on-code)',
      border: 'var(--border-width) solid var(--border-code)',
      borderRadius: variant === 'mockup' ? 'var(--radius-lg)' : 'var(--radius-md)',
      boxShadow: variant === 'mockup' ? 'var(--shadow-3)' : 'none',
      overflow: 'hidden',
      ...style
    }
  }, rest), header && /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 'var(--space-sm)',
      padding: 'var(--space-xs) var(--space-md)',
      borderBottom: 'var(--border-width) solid rgba(255, 255, 255, 0.08)',
      font: 'var(--type-caption)',
      color: 'var(--text-on-code-muted)'
    }
  }, filename && /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 6,
      color: 'var(--text-on-code)'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "file-text",
    size: 14
  }), filename), /*#__PURE__*/React.createElement("span", {
    style: {
      marginLeft: 'auto'
    }
  }, language), copyable && /*#__PURE__*/React.createElement("button", {
    type: "button",
    onClick: copy,
    "aria-label": copied ? 'Copied' : 'Copy code',
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 6,
      padding: '4px 8px',
      marginRight: -8,
      border: 0,
      borderRadius: 'var(--radius-xs)',
      background: 'transparent',
      color: copied ? 'var(--code-keyword)' : 'var(--text-on-code-muted)',
      font: 'var(--type-caption)',
      cursor: 'pointer'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: copied ? 'check' : 'copy',
    size: 14
  }), copied ? 'Copied' : 'Copy')), /*#__PURE__*/React.createElement("pre", {
    style: {
      margin: 0,
      padding: 'var(--space-md)',
      font: 'var(--type-code-md)',
      overflow: 'auto',
      maxHeight,
      tabSize: 2
    }
  }, /*#__PURE__*/React.createElement("code", {
    style: {
      font: 'inherit'
    }
  }, lines.map((line, i) => /*#__PURE__*/React.createElement("div", {
    key: i,
    style: {
      display: 'flex',
      minHeight: '1.5em'
    }
  }, lineNumbers && /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      flex: '0 0 auto',
      width: gutter + 2 + 'ch',
      color: 'var(--text-on-code-muted)',
      opacity: 0.7,
      userSelect: 'none'
    }
  }, i + 1), /*#__PURE__*/React.createElement("span", {
    style: {
      whiteSpace: 'pre'
    }
  }, highlight ? tokenize(line, hash).map(([k, t], j) => k === 'ws' ? t : /*#__PURE__*/React.createElement("span", {
    key: j,
    style: {
      color: COLORS[k]
    }
  }, t)) : line))))));
}
Object.assign(__ds_scope, { CodeBlock });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/code/CodeBlock.jsx", error: String((e && e.message) || e) }); }

// components/content/FeatureIconTile.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function FeatureIconTile({
  icon = 'sparkles',
  title,
  children,
  layout = 'stacked',
  style,
  ...rest
}) {
  const tile = /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'inline-grid',
      placeItems: 'center',
      flex: '0 0 auto',
      padding: 'var(--space-md)',
      borderRadius: 'var(--radius-md)',
      background: 'var(--surface-warm)',
      border: 'var(--border-width) solid var(--border-warm)',
      color: 'var(--text-accent)'
    }
  }, typeof icon === 'string' ? /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: icon,
    size: 24
  }) : icon);
  if (!title && !children) return /*#__PURE__*/React.createElement("span", _extends({
    style: style
  }, rest), tile);
  const inline = layout === 'inline';
  return /*#__PURE__*/React.createElement("div", _extends({
    style: {
      display: 'flex',
      flexDirection: inline ? 'row' : 'column',
      alignItems: 'flex-start',
      gap: inline ? 'var(--space-lg)' : 'var(--space-lg)',
      minWidth: 0,
      ...style
    }
  }, rest), tile, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xs)',
      minWidth: 0
    }
  }, title && /*#__PURE__*/React.createElement("h3", {
    style: {
      margin: 0,
      font: 'var(--type-heading-4)',
      color: 'var(--text-primary)'
    }
  }, title), children && /*#__PURE__*/React.createElement("div", {
    style: {
      font: 'var(--type-body-md)',
      color: 'var(--text-secondary)',
      textWrap: 'pretty'
    }
  }, children)));
}
Object.assign(__ds_scope, { FeatureIconTile });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/content/FeatureIconTile.jsx", error: String((e && e.message) || e) }); }

// components/content/IndustryTile.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function IndustryTile({
  icon = 'building-2',
  title,
  children,
  href,
  linkLabel = 'Learn more',
  onClick,
  style,
  ...rest
}) {
  const [pressed, setPressed] = React.useState(false);
  const Tag = href ? 'a' : 'div';
  return /*#__PURE__*/React.createElement(Tag, _extends({
    href: href,
    onClick: onClick,
    onPointerDown: () => setPressed(true),
    onPointerUp: () => setPressed(false),
    onPointerLeave: () => setPressed(false),
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)',
      minWidth: 0,
      padding: 'var(--space-xl)',
      borderRadius: 'var(--radius-card)',
      background: 'var(--surface-card)',
      border: 'var(--border-width) solid ' + (pressed && href ? 'var(--border-strong)' : 'var(--border-subtle)'),
      color: 'var(--text-primary)',
      textDecoration: 'none',
      cursor: href || onClick ? 'pointer' : undefined,
      transition: 'border-color var(--duration-fast) var(--ease-standard)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("span", {
    style: {
      color: 'var(--text-primary)',
      marginBottom: 'var(--space-xs)'
    }
  }, typeof icon === 'string' ? /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: icon,
    size: 24
  }) : icon), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-heading-5)'
    }
  }, title), children && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm)',
      color: 'var(--text-secondary)',
      textWrap: 'pretty'
    }
  }, children), linkLabel && (href || onClick) && /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 6,
      marginTop: 'auto',
      paddingTop: 'var(--space-xs)',
      font: 'var(--type-body-sm-medium)',
      color: 'var(--text-link)'
    }
  }, linkLabel, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "arrow-right",
    size: 16
  })));
}
Object.assign(__ds_scope, { IndustryTile });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/content/IndustryTile.jsx", error: String((e && e.message) || e) }); }

// components/cooking/StepTimer.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
const pad = n => String(n).padStart(2, '0');
function formatClock(sec) {
  const s = Math.max(0, Math.ceil(sec));
  const h = Math.floor(s / 3600);
  const m = Math.floor(s % 3600 / 60);
  return h ? `${h}:${pad(m)}:${pad(s % 60)}` : `${m}:${pad(s % 60)}`;
}
function formatIdle(sec) {
  if (sec % 60 !== 0) return formatClock(sec);
  const h = Math.floor(sec / 3600);
  const m = sec % 3600 / 60;
  if (!h) return `${m} min`;
  return m ? `${h} h ${m} min` : `${h} h`;
}
function StepTimer({
  duration,
  label,
  initialStatus = 'idle',
  initialRemaining,
  onStart,
  onPause,
  onComplete,
  onReset,
  style,
  ...rest
}) {
  const [status, setStatus] = React.useState(initialStatus);
  const [remaining, setRemaining] = React.useState(initialRemaining ?? (initialStatus === 'done' ? 0 : duration));
  const [pressed, setPressed] = React.useState(false);
  const endRef = React.useRef(0);
  const doneRef = React.useRef(onComplete);
  doneRef.current = onComplete;
  React.useEffect(() => {
    if (status !== 'running') return undefined;
    endRef.current = Date.now() + remaining * 1000;
    const id = setInterval(() => {
      const left = (endRef.current - Date.now()) / 1000;
      if (left <= 0) {
        clearInterval(id);
        setRemaining(0);
        setStatus('done');
        try {
          navigator.vibrate && navigator.vibrate([200, 100, 200]);
        } catch (e) {}
        doneRef.current && doneRef.current();
      } else {
        setRemaining(left);
      }
    }, 250);
    return () => clearInterval(id);
  }, [status]);
  const running = status === 'running';
  const paused = status === 'paused';
  const done = status === 'done';
  const idle = status === 'idle';
  const toggle = () => {
    if (idle) {
      setStatus('running');
      onStart && onStart();
    } else if (running) {
      setStatus('paused');
      onPause && onPause(remaining);
    } else if (paused) {
      setStatus('running');
      onStart && onStart();
    } else reset();
  };
  const reset = () => {
    setStatus('idle');
    setRemaining(duration);
    onReset && onReset();
  };
  const progress = duration ? Math.min(1, Math.max(0, 1 - remaining / duration)) : 0;
  const filled = idle || running;
  const text = idle ? label || formatIdle(duration) : done ? 'Done' : formatClock(remaining);
  const icon = idle ? 'timer' : running ? 'pause' : paused ? 'play' : 'check';
  const aria = idle ? `Start ${formatIdle(duration)} timer` : running ? `Pause timer, ${formatClock(remaining)} left` : paused ? `Resume timer, ${formatClock(remaining)} left` : 'Timer finished, tap to reset';
  return /*#__PURE__*/React.createElement("span", _extends({
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 'var(--space-xs)',
      flexShrink: 0,
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("button", {
    type: "button",
    onClick: toggle,
    "aria-label": aria,
    onPointerDown: () => setPressed(true),
    onPointerUp: () => setPressed(false),
    onPointerLeave: () => setPressed(false),
    onPointerCancel: () => setPressed(false),
    style: {
      position: 'relative',
      display: 'inline-flex',
      alignItems: 'center',
      justifyContent: 'center',
      gap: 8,
      minWidth: 104,
      minHeight: 44,
      padding: '0 16px 0 14px',
      borderRadius: 'var(--radius-md)',
      borderStyle: 'solid',
      borderWidth: 'var(--border-width)',
      borderColor: done ? 'var(--surface-inverse)' : filled && pressed ? 'var(--accent-pressed)' : 'var(--accent)',
      background: done ? 'var(--surface-inverse)' : filled ? pressed ? 'var(--accent-pressed)' : 'var(--accent)' : 'transparent',
      color: done ? 'var(--text-inverse)' : filled ? 'var(--accent-fg)' : 'var(--text-accent)',
      font: 'var(--type-button-md)',
      fontSize: 15,
      fontVariantNumeric: 'tabular-nums',
      whiteSpace: 'nowrap',
      cursor: 'pointer',
      overflow: 'hidden',
      WebkitTapHighlightColor: 'transparent',
      transition: 'background-color var(--duration-fast) var(--ease-standard), border-color var(--duration-fast) var(--ease-standard), color var(--duration-fast) var(--ease-standard)'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: icon,
    size: 18
  }), /*#__PURE__*/React.createElement("span", {
    role: "timer",
    "aria-live": done ? 'polite' : 'off'
  }, text), (running || paused) && /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      position: 'absolute',
      left: 12,
      right: 12,
      bottom: 5,
      height: 3,
      borderRadius: 'var(--radius-full)',
      background: running ? 'rgba(0, 0, 0, 0.22)' : 'var(--border-subtle)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'block',
      height: '100%',
      width: progress * 100 + '%',
      borderRadius: 'var(--radius-full)',
      background: running ? 'var(--accent-fg)' : 'var(--accent)',
      transition: 'width 250ms linear'
    }
  }))), (paused || done) && /*#__PURE__*/React.createElement("button", {
    type: "button",
    onClick: reset,
    "aria-label": "Reset timer",
    style: {
      display: 'grid',
      placeItems: 'center',
      width: 44,
      height: 44,
      padding: 0,
      borderRadius: 'var(--radius-md)',
      border: 'var(--border-width) solid var(--border-default)',
      background: 'transparent',
      color: 'var(--text-secondary)',
      cursor: 'pointer',
      WebkitTapHighlightColor: 'transparent'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "rotate-ccw",
    size: 18
  })));
}
Object.assign(__ds_scope, { formatClock, StepTimer });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/cooking/StepTimer.jsx", error: String((e && e.message) || e) }); }

// components/cooking/CookingStep.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function CookingStep({
  number,
  title,
  duration,
  timerLabel,
  onTimerStart,
  onTimerComplete,
  variant = 'card',
  active = false,
  children,
  style,
  ...rest
}) {
  const card = variant === 'card';
  return /*#__PURE__*/React.createElement("section", _extends({
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)',
      minWidth: 0,
      padding: card ? 'var(--space-lg)' : 'var(--space-lg) 0',
      borderRadius: card ? 'var(--radius-card)' : 0,
      background: card ? 'var(--surface-card)' : 'transparent',
      border: card ? active ? 'var(--border-width-strong) solid var(--border-accent)' : 'var(--border-width) solid var(--border-subtle)' : 0,
      borderBottom: card ? undefined : 'var(--border-width) solid var(--border-subtle)',
      color: 'var(--text-primary)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: 'var(--space-md)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 2,
      minWidth: 0
    }
  }, number != null && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-micro-uppercase)',
      letterSpacing: 'var(--tracking-micro-uppercase)',
      textTransform: 'uppercase',
      color: active ? 'var(--text-accent)' : 'var(--text-tertiary)'
    }
  }, "Step ", number), /*#__PURE__*/React.createElement("h3", {
    style: {
      margin: 0,
      font: 'var(--type-heading-4)',
      textWrap: 'balance'
    }
  }, title)), duration > 0 && /*#__PURE__*/React.createElement(__ds_scope.StepTimer, {
    duration: duration,
    label: timerLabel,
    onStart: onTimerStart,
    onComplete: onTimerComplete
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      font: 'var(--type-body-md)',
      color: 'var(--text-secondary)',
      textWrap: 'pretty'
    }
  }, children));
}
Object.assign(__ds_scope, { CookingStep });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/cooking/CookingStep.jsx", error: String((e && e.message) || e) }); }

// components/faq/FaqItem.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function FaqItem({
  question,
  children,
  defaultOpen = false,
  open,
  onToggle,
  style,
  ...rest
}) {
  const [inner, setInner] = React.useState(defaultOpen);
  const isOpen = open !== undefined ? open : inner;
  const autoId = React.useId ? React.useId() : 'faq';
  const toggle = () => {
    if (open === undefined) setInner(!isOpen);
    onToggle && onToggle(!isOpen);
  };
  return /*#__PURE__*/React.createElement("div", _extends({
    style: {
      background: 'var(--surface-card)',
      borderBottom: 'var(--border-width) solid var(--border-default)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("button", {
    type: "button",
    id: autoId + '-q',
    "aria-expanded": isOpen,
    "aria-controls": autoId + '-a',
    onClick: toggle,
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: 'var(--space-md)',
      width: '100%',
      padding: 'var(--space-xl) 0',
      border: 0,
      borderRadius: 'var(--radius-md)',
      background: 'transparent',
      color: 'var(--text-primary)',
      font: 'var(--type-heading-5)',
      textAlign: 'left',
      cursor: 'pointer'
    }
  }, /*#__PURE__*/React.createElement("span", null, question), /*#__PURE__*/React.createElement("span", {
    style: {
      flex: '0 0 auto',
      color: 'var(--text-tertiary)',
      transform: isOpen ? 'rotate(180deg)' : 'none',
      transition: 'transform var(--duration-base) var(--ease-standard)'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "chevron-down",
    size: 20
  }))), isOpen && /*#__PURE__*/React.createElement("div", {
    id: autoId + '-a',
    role: "region",
    "aria-labelledby": autoId + '-q',
    style: {
      padding: '0 0 var(--space-xl)',
      font: 'var(--type-body-md)',
      color: 'var(--text-secondary)',
      maxWidth: 720,
      textWrap: 'pretty'
    }
  }, children));
}
Object.assign(__ds_scope, { FaqItem });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/faq/FaqItem.jsx", error: String((e && e.message) || e) }); }

// components/footer/Footer.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function useWidth(ref) {
  const [w, setW] = React.useState(1280);
  React.useEffect(() => {
    const el = ref.current;
    if (!el || typeof ResizeObserver === 'undefined') return;
    const ro = new ResizeObserver(([e]) => setW(e.contentRect.width));
    ro.observe(el);
    return () => ro.disconnect();
  }, []);
  return w;
}
const DEFAULT_COLUMNS = [{
  title: 'Why us',
  links: ['Customers', 'Security', 'Pricing', 'Changelog']
}, {
  title: 'Explore',
  links: ['Products', 'Solutions', 'Blog', 'Events']
}, {
  title: 'Build',
  links: ['Documentation', 'API reference', 'Status', 'Community']
}, {
  title: 'Legal',
  links: ['Terms', 'Privacy', 'Cookies', 'Licences']
}].map(c => ({
  ...c,
  links: c.links.map(label => ({
    label,
    href: '#'
  }))
}));
const DEFAULT_SOCIAL = ['GitHub', 'X', 'LinkedIn', 'YouTube'].map(label => ({
  label,
  href: '#'
}));
function Footer({
  brand = 'Simmer',
  tagline = 'Ideas, brought into focus.',
  columns = DEFAULT_COLUMNS,
  social = DEFAULT_SOCIAL,
  language = 'English',
  copyright = '© 2026 Simmer',
  aside,
  onNavigate,
  style,
  ...rest
}) {
  const ref = React.useRef(null);
  const w = useWidth(ref);
  const cols = w < 768 ? 1 : w < 1024 ? 3 : 5;
  const go = l => e => {
    if (onNavigate) {
      e.preventDefault();
      onNavigate(l.href, l);
    }
  };
  return /*#__PURE__*/React.createElement("footer", _extends({
    ref: ref,
    style: {
      background: 'var(--surface-footer)',
      color: 'var(--text-primary)',
      padding: 'var(--space-section) var(--space-xxl)',
      font: 'var(--type-body-sm)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("div", {
    style: {
      maxWidth: 'var(--container-max)',
      margin: '0 auto',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-section-sm)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'grid',
      gridTemplateColumns: cols === 5 ? '1.6fr repeat(4, minmax(0, 1fr))' : `repeat(${cols}, minmax(0, 1fr))`,
      gap: 'var(--space-xxl)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)',
      gridColumn: cols === 3 ? '1 / -1' : undefined
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: '400 28px/1 var(--font-display)',
      letterSpacing: '-0.3px'
    }
  }, brand), tagline && /*#__PURE__*/React.createElement("span", {
    style: {
      color: 'var(--text-tertiary)',
      maxWidth: 260
    }
  }, tagline), aside && /*#__PURE__*/React.createElement("div", {
    style: {
      marginTop: 'var(--space-xs)'
    }
  }, aside)), columns.map(c => /*#__PURE__*/React.createElement("div", {
    key: c.title,
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xxs)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-micro-uppercase)',
      letterSpacing: 'var(--tracking-micro-uppercase)',
      textTransform: 'uppercase',
      color: 'var(--text-tertiary)',
      marginBottom: 'var(--space-xs)'
    }
  }, c.title), c.links.map(l => /*#__PURE__*/React.createElement(__ds_scope.FooterLink, {
    key: l.label,
    href: l.href,
    onClick: go(l)
  }, l.label))))), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: 'var(--space-md)',
      paddingTop: 'var(--space-xl)',
      borderTop: 'var(--border-width) solid var(--border-warm)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 'var(--space-md)',
      flexWrap: 'wrap'
    }
  }, /*#__PURE__*/React.createElement("button", {
    type: "button",
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 8,
      padding: '6px 12px',
      borderRadius: 'var(--radius-md)',
      border: 'var(--border-width) solid var(--border-warm)',
      background: 'transparent',
      color: 'var(--text-primary)',
      font: 'var(--type-body-sm-medium)',
      cursor: 'pointer'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "globe",
    size: 16
  }), language, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "chevron-down",
    size: 16
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-micro)',
      color: 'var(--text-tertiary)'
    }
  }, copyright)), /*#__PURE__*/React.createElement("nav", {
    "aria-label": "Social",
    style: {
      display: 'flex',
      gap: 'var(--space-lg)',
      flexWrap: 'wrap'
    }
  }, social.map(s => /*#__PURE__*/React.createElement("a", {
    key: s.label,
    href: s.href,
    style: {
      font: 'var(--type-body-sm-medium)',
      color: 'var(--text-secondary)',
      textDecoration: 'none'
    }
  }, s.label))))));
}
Object.assign(__ds_scope, { Footer });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/footer/Footer.jsx", error: String((e && e.message) || e) }); }

// components/forms/TextInput.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function TextInput({
  label,
  helperText,
  id,
  type = 'text',
  iconLeft,
  disabled = false,
  required = false,
  style,
  inputStyle,
  onFocus,
  onBlur,
  ...rest
}) {
  const [focused, setFocused] = React.useState(false);
  const autoId = React.useId ? React.useId() : undefined;
  const inputId = id || autoId;
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xs)',
      minWidth: 0,
      ...style
    }
  }, label && /*#__PURE__*/React.createElement("label", {
    htmlFor: inputId,
    style: {
      font: 'var(--type-body-sm-medium)',
      color: 'var(--text-primary)'
    }
  }, label, required && /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      color: 'var(--text-accent)'
    }
  }, " *")), /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'relative',
      display: 'flex',
      alignItems: 'center'
    }
  }, iconLeft && /*#__PURE__*/React.createElement("span", {
    style: {
      position: 'absolute',
      left: 14,
      color: 'var(--text-tertiary)',
      pointerEvents: 'none'
    }
  }, typeof iconLeft === 'string' ? /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: iconLeft,
    size: 18
  }) : iconLeft), /*#__PURE__*/React.createElement("input", _extends({
    id: inputId,
    type: type,
    disabled: disabled,
    required: required,
    onFocus: e => {
      setFocused(true);
      onFocus && onFocus(e);
    },
    onBlur: e => {
      setFocused(false);
      onBlur && onBlur(e);
    },
    style: {
      width: '100%',
      height: 44,
      padding: iconLeft ? 'var(--space-sm) var(--space-md) var(--space-sm) 42px' : 'var(--space-sm) var(--space-md)',
      font: 'var(--type-body-md)',
      color: disabled ? 'var(--text-disabled)' : 'var(--text-primary)',
      background: disabled ? 'var(--surface-subtle)' : 'var(--surface-card)',
      border: 'var(--border-width) solid ' + (focused ? 'var(--border-accent)' : disabled ? 'var(--border-default)' : 'var(--border-strong)'),
      boxShadow: focused ? 'inset 0 0 0 1px var(--border-accent)' : 'none',
      borderRadius: 'var(--radius-input)',
      outline: 'none',
      transition: 'border-color var(--duration-fast) var(--ease-standard), box-shadow var(--duration-fast) var(--ease-standard)',
      ...inputStyle
    }
  }, rest))), helperText && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-caption)',
      color: 'var(--text-tertiary)'
    }
  }, helperText));
}
Object.assign(__ds_scope, { TextInput });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/TextInput.jsx", error: String((e && e.message) || e) }); }

// components/navigation/TopNav.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function useNarrow(ref, breakpoint) {
  const [narrow, setNarrow] = React.useState(false);
  React.useEffect(() => {
    const el = ref.current;
    if (!el || typeof ResizeObserver === 'undefined') return;
    const ro = new ResizeObserver(([e]) => setNarrow(e.contentRect.width < breakpoint));
    ro.observe(el);
    return () => ro.disconnect();
  }, [breakpoint]);
  return narrow;
}
const DEFAULT_LINKS = ['Products', 'Solutions', 'Resources', 'Blog', 'Customers', 'Company'].map(label => ({
  label,
  href: '#'
}));
function TopNav({
  brand = 'Simmer',
  brandHref = '#',
  links = DEFAULT_LINKS,
  secondaryAction = {
    label: 'Contact sales',
    href: '#'
  },
  primaryAction = {
    label: 'Get started',
    href: '#'
  },
  extra,
  onNavigate,
  sticky = true,
  collapseBelow = 1024,
  style,
  ...rest
}) {
  const ref = React.useRef(null);
  const narrow = useNarrow(ref, collapseBelow);
  const [open, setOpen] = React.useState(false);
  React.useEffect(() => {
    if (!narrow) setOpen(false);
  }, [narrow]);
  const go = item => e => {
    if (onNavigate) {
      e.preventDefault();
      onNavigate(item.href, item);
    }
    setOpen(false);
  };
  const linkStyle = active => ({
    font: 'var(--type-body-sm-medium)',
    color: active ? 'var(--text-primary)' : 'var(--text-secondary)',
    textDecoration: 'none',
    whiteSpace: 'nowrap'
  });
  return /*#__PURE__*/React.createElement("header", _extends({
    ref: ref,
    style: {
      position: sticky ? 'sticky' : 'relative',
      top: 0,
      zIndex: 20,
      background: 'var(--surface-page)',
      borderBottom: 'var(--border-width) solid var(--border-subtle)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 'var(--space-xxl)',
      height: 'var(--nav-height)',
      maxWidth: 'var(--container-max)',
      margin: '0 auto',
      padding: '0 var(--container-gutter)'
    }
  }, /*#__PURE__*/React.createElement("a", {
    href: brandHref,
    onClick: go({
      href: brandHref,
      label: 'home'
    }),
    style: {
      font: '400 26px/1 var(--font-display)',
      letterSpacing: '-0.3px',
      color: 'var(--text-primary)',
      textDecoration: 'none',
      whiteSpace: 'nowrap'
    }
  }, brand), !narrow && /*#__PURE__*/React.createElement("nav", {
    "aria-label": "Primary",
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 'var(--space-xl)'
    }
  }, links.map(l => /*#__PURE__*/React.createElement("a", {
    key: l.label,
    href: l.href,
    onClick: go(l),
    "aria-current": l.active ? 'page' : undefined,
    style: linkStyle(l.active)
  }, l.label))), /*#__PURE__*/React.createElement("div", {
    style: {
      marginLeft: 'auto',
      display: 'flex',
      alignItems: 'center',
      gap: 'var(--space-md)'
    }
  }, extra, !narrow && secondaryAction && /*#__PURE__*/React.createElement("a", {
    href: secondaryAction.href,
    onClick: go(secondaryAction),
    style: {
      ...linkStyle(true)
    }
  }, secondaryAction.label), !narrow && primaryAction && /*#__PURE__*/React.createElement(__ds_scope.Button, {
    variant: "dark",
    href: primaryAction.href,
    onClick: primaryAction.onClick || go(primaryAction)
  }, primaryAction.label), narrow && /*#__PURE__*/React.createElement("button", {
    type: "button",
    "aria-label": open ? 'Close menu' : 'Open menu',
    "aria-expanded": open,
    onClick: () => setOpen(o => !o),
    style: {
      display: 'grid',
      placeItems: 'center',
      width: 44,
      height: 44,
      padding: 0,
      borderRadius: 'var(--radius-md)',
      border: 'var(--border-width) solid var(--border-default)',
      background: 'var(--surface-card)',
      color: 'var(--text-primary)',
      cursor: 'pointer'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: open ? 'x' : 'menu',
    size: 20
  })))), narrow && open && /*#__PURE__*/React.createElement("div", {
    style: {
      borderTop: 'var(--border-width) solid var(--border-subtle)',
      padding: 'var(--space-md) var(--container-gutter) var(--space-xl)',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xxs)',
      background: 'var(--surface-page)'
    }
  }, links.map(l => /*#__PURE__*/React.createElement("a", {
    key: l.label,
    href: l.href,
    onClick: go(l),
    style: {
      ...linkStyle(l.active),
      font: 'var(--type-body-md-medium)',
      padding: '10px 0'
    }
  }, l.label)), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)',
      marginTop: 'var(--space-md)'
    }
  }, secondaryAction && /*#__PURE__*/React.createElement(__ds_scope.Button, {
    variant: "secondary",
    href: secondaryAction.href,
    onClick: go(secondaryAction),
    fullWidth: true
  }, secondaryAction.label), primaryAction && /*#__PURE__*/React.createElement(__ds_scope.Button, {
    variant: "dark",
    href: primaryAction.href,
    onClick: primaryAction.onClick || go(primaryAction),
    fullWidth: true
  }, primaryAction.label))));
}
Object.assign(__ds_scope, { TopNav });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/navigation/TopNav.jsx", error: String((e && e.message) || e) }); }

// components/pricing/PricingCard.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function PricingCard({
  name,
  price,
  period,
  description,
  features = [],
  ctaLabel = 'Get started',
  ctaHref,
  onCtaClick,
  featured = false,
  badge,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("div", _extends({
    style: {
      position: 'relative',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xl)',
      minWidth: 0,
      padding: 'var(--space-xxl)',
      borderRadius: 'var(--radius-card)',
      background: featured ? 'var(--surface-warm)' : 'var(--surface-card)',
      border: featured ? 'var(--border-width-strong) solid var(--border-accent)' : 'var(--border-width) solid var(--border-subtle)',
      color: 'var(--text-primary)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xs)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: 'var(--space-xs)',
      minHeight: 26
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-heading-5)'
    }
  }, name), badge && /*#__PURE__*/React.createElement(__ds_scope.Badge, {
    variant: "primary"
  }, badge)), description && /*#__PURE__*/React.createElement("p", {
    style: {
      margin: 0,
      minHeight: 42,
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)',
      textWrap: 'pretty'
    }
  }, description)), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'baseline',
      gap: 6,
      flexWrap: 'wrap'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-stat-display)',
      letterSpacing: 'var(--tracking-stat-display)'
    }
  }, price), period && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)'
    }
  }, period)), /*#__PURE__*/React.createElement(__ds_scope.Button, {
    variant: featured ? 'primary' : 'secondary',
    href: ctaHref,
    onClick: onCtaClick,
    fullWidth: true
  }, ctaLabel), features.length > 0 && /*#__PURE__*/React.createElement("ul", {
    style: {
      listStyle: 'none',
      margin: 0,
      padding: 'var(--space-xl) 0 0',
      borderTop: 'var(--border-width) solid ' + (featured ? 'var(--border-warm)' : 'var(--border-subtle)'),
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)'
    }
  }, features.map((f, i) => /*#__PURE__*/React.createElement("li", {
    key: i,
    style: {
      display: 'flex',
      gap: 10,
      alignItems: 'flex-start',
      font: 'var(--type-body-sm)',
      color: 'var(--text-secondary)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      color: 'var(--text-accent)',
      paddingTop: 1
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "check",
    size: 18
  })), /*#__PURE__*/React.createElement("span", null, f)))));
}
Object.assign(__ds_scope, { PricingCard });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/pricing/PricingCard.jsx", error: String((e && e.message) || e) }); }

// components/shopping/ShoppingListItem.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function ShoppingListItem({
  name,
  amount,
  note,
  checked = false,
  onToggle,
  divider = false,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("li", _extends({
    style: {
      listStyle: 'none',
      borderTop: divider ? 'var(--border-width) solid var(--border-subtle)' : 0,
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("button", {
    type: "button",
    role: "checkbox",
    "aria-checked": checked,
    onClick: onToggle,
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 'var(--space-md)',
      width: '100%',
      minHeight: 56,
      margin: 0,
      padding: 'var(--space-sm) var(--space-md)',
      border: 0,
      background: 'transparent',
      color: 'inherit',
      font: 'inherit',
      textAlign: 'left',
      cursor: 'pointer',
      WebkitTapHighlightColor: 'transparent'
    }
  }, /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      flex: '0 0 auto',
      display: 'grid',
      placeItems: 'center',
      width: 24,
      height: 24,
      borderRadius: 'var(--radius-sm)',
      border: 'var(--border-width) solid ' + (checked ? 'var(--accent)' : 'var(--border-strong)'),
      background: checked ? 'var(--accent)' : 'transparent',
      color: 'var(--accent-fg)',
      transition: 'background-color var(--duration-fast) var(--ease-standard), border-color var(--duration-fast) var(--ease-standard)'
    }
  }, checked && /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "check",
    size: 16,
    stroke: 2.5
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      flex: '1 1 auto',
      minWidth: 0,
      display: 'flex',
      flexDirection: 'column',
      gap: 2
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-md)',
      color: checked ? 'var(--text-tertiary)' : 'var(--text-primary)',
      textDecorationLine: checked ? 'line-through' : 'none',
      textDecorationThickness: 2,
      overflowWrap: 'anywhere'
    }
  }, name), note && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-caption)',
      color: 'var(--text-tertiary)'
    }
  }, note)), amount && /*#__PURE__*/React.createElement("span", {
    style: {
      flex: '0 0 auto',
      font: 'var(--type-body-sm-medium)',
      color: 'var(--text-tertiary)',
      fontVariantNumeric: 'tabular-nums',
      whiteSpace: 'nowrap'
    }
  }, amount)));
}
Object.assign(__ds_scope, { ShoppingListItem });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/shopping/ShoppingListItem.jsx", error: String((e && e.message) || e) }); }

// components/shopping/ShoppingList.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
let seq = 0;
function Group({
  label,
  items,
  onToggle
}) {
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xs)'
    }
  }, label && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-micro-uppercase)',
      letterSpacing: 'var(--tracking-micro-uppercase)',
      textTransform: 'uppercase',
      color: 'var(--text-tertiary)',
      padding: '0 2px'
    }
  }, label), /*#__PURE__*/React.createElement("ul", {
    style: {
      margin: 0,
      padding: 0,
      borderRadius: 'var(--radius-card)',
      border: 'var(--border-width) solid var(--border-subtle)',
      background: 'var(--surface-card)',
      overflow: 'hidden'
    }
  }, items.map((it, i) => /*#__PURE__*/React.createElement(__ds_scope.ShoppingListItem, {
    key: it.id,
    name: it.name,
    amount: it.amount,
    note: it.note,
    checked: !!it.checked,
    divider: i > 0,
    onToggle: () => onToggle(it.id)
  }))));
}
function ShoppingList({
  title = 'Shopping list',
  items,
  defaultItems = [],
  onChange,
  groupByCategory,
  moveCheckedToBottom = true,
  showProgress = true,
  allowAdd = false,
  addPlaceholder = 'Add an item',
  style,
  ...rest
}) {
  const controlled = items !== undefined;
  const [inner, setInner] = React.useState(defaultItems);
  const [draft, setDraft] = React.useState('');
  const list = controlled ? items : inner;
  const update = next => {
    if (!controlled) setInner(next);
    onChange && onChange(next);
  };
  const toggle = id => update(list.map(it => it.id === id ? {
    ...it,
    checked: !it.checked
  } : it));
  const clearChecked = () => update(list.filter(it => !it.checked));
  const add = e => {
    e.preventDefault();
    const name = draft.trim();
    if (!name) return;
    update([...list, {
      id: 'item-' + Date.now() + '-' + seq++,
      name,
      checked: false
    }]);
    setDraft('');
  };
  const total = list.length;
  const left = list.filter(it => !it.checked).length;
  const bought = total - left;
  const grouped = groupByCategory ?? list.some(it => it.category);
  const active = moveCheckedToBottom ? list.filter(it => !it.checked) : list;
  const basket = moveCheckedToBottom ? list.filter(it => it.checked) : [];
  const groups = [];
  if (grouped) {
    const map = new Map();
    active.forEach(it => {
      const k = it.category || 'Other';
      if (!map.has(k)) {
        map.set(k, []);
        groups.push(k);
      }
      map.get(k).push(it);
    });
    groups.forEach((k, i) => {
      groups[i] = [k, map.get(k)];
    });
  } else if (active.length) {
    groups.push([null, active]);
  }
  return /*#__PURE__*/React.createElement("div", _extends({
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-lg)',
      minWidth: 0,
      color: 'var(--text-primary)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: 'var(--space-md)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 2,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("h2", {
    style: {
      margin: 0,
      font: 'var(--type-heading-4)'
    }
  }, title), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)'
    }
  }, total === 0 ? 'Nothing on the list' : left === 0 ? 'Everything is in your basket' : `${left} of ${total} left`)), bought > 0 && /*#__PURE__*/React.createElement("button", {
    type: "button",
    onClick: clearChecked,
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 6,
      minHeight: 44,
      padding: '0 var(--space-xxs)',
      border: 0,
      background: 'transparent',
      color: 'var(--text-link)',
      font: 'var(--type-body-sm-medium)',
      cursor: 'pointer',
      whiteSpace: 'nowrap'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "trash-2",
    size: 16
  }), "Clear checked")), showProgress && total > 0 && /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      display: 'block',
      height: 4,
      borderRadius: 'var(--radius-full)',
      background: 'var(--border-subtle)',
      overflow: 'hidden'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'block',
      height: '100%',
      width: bought / total * 100 + '%',
      borderRadius: 'var(--radius-full)',
      background: 'var(--accent)',
      transition: 'width var(--duration-base) var(--ease-out)'
    }
  }))), allowAdd && /*#__PURE__*/React.createElement("form", {
    onSubmit: add,
    style: {
      display: 'flex',
      gap: 'var(--space-xs)'
    }
  }, /*#__PURE__*/React.createElement("input", {
    value: draft,
    onChange: e => setDraft(e.target.value),
    placeholder: addPlaceholder,
    "aria-label": addPlaceholder,
    style: {
      flex: '1 1 auto',
      minWidth: 0,
      height: 44,
      padding: 'var(--space-sm) var(--space-md)',
      font: 'var(--type-body-md)',
      color: 'var(--text-primary)',
      background: 'var(--surface-card)',
      border: 'var(--border-width) solid var(--border-strong)',
      borderRadius: 'var(--radius-input)',
      outline: 'none'
    },
    onFocus: e => {
      e.target.style.borderColor = 'var(--border-accent)';
      e.target.style.boxShadow = 'inset 0 0 0 1px var(--border-accent)';
    },
    onBlur: e => {
      e.target.style.borderColor = 'var(--border-strong)';
      e.target.style.boxShadow = 'none';
    }
  }), /*#__PURE__*/React.createElement("button", {
    type: "submit",
    "aria-label": "Add item",
    style: {
      flex: '0 0 auto',
      display: 'grid',
      placeItems: 'center',
      width: 44,
      height: 44,
      padding: 0,
      borderRadius: 'var(--radius-button)',
      border: 'var(--border-width) solid var(--accent)',
      background: 'var(--accent)',
      color: 'var(--accent-fg)',
      cursor: 'pointer'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "plus",
    size: 20
  }))), groups.map(([label, its]) => /*#__PURE__*/React.createElement(Group, {
    key: label || 'all',
    label: label,
    items: its,
    onToggle: toggle
  })), basket.length > 0 && /*#__PURE__*/React.createElement(Group, {
    label: `In your basket · ${basket.length}`,
    items: basket,
    onToggle: toggle
  }), total === 0 && !allowAdd && /*#__PURE__*/React.createElement("div", {
    style: {
      padding: 'var(--space-xl)',
      borderRadius: 'var(--radius-card)',
      background: 'var(--surface-warm)',
      border: 'var(--border-width) solid var(--border-warm)',
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)',
      textAlign: 'center'
    }
  }, "Add ingredients from a recipe to build your list."));
}
Object.assign(__ds_scope, { ShoppingList });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/shopping/ShoppingList.jsx", error: String((e && e.message) || e) }); }

// components/signature/CtaBanner.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function useWidth(ref) {
  const [w, setW] = React.useState(1200);
  React.useEffect(() => {
    const el = ref.current;
    if (!el || typeof ResizeObserver === 'undefined') return;
    const ro = new ResizeObserver(([e]) => setW(e.contentRect.width));
    ro.observe(el);
    return () => ro.disconnect();
  }, []);
  return w;
}
function CtaBanner({
  title,
  subtitle,
  actions,
  align = 'center',
  style,
  ...rest
}) {
  const ref = React.useRef(null);
  const w = useWidth(ref);
  const narrow = w < 640;
  const center = align === 'center';
  return /*#__PURE__*/React.createElement("section", _extends({
    ref: ref,
    style: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: center ? 'center' : 'flex-start',
      textAlign: center ? 'center' : 'left',
      gap: 'var(--space-md)',
      padding: narrow ? 'var(--space-xxl)' : 'var(--space-section)',
      borderRadius: 'var(--radius-card)',
      background: 'var(--surface-warm)',
      border: 'var(--border-width) solid var(--border-warm)',
      color: 'var(--text-on-warm)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("h2", {
    style: {
      margin: 0,
      font: narrow ? '400 40px/1.15 var(--font-display)' : 'var(--type-heading-1)',
      letterSpacing: 'var(--tracking-heading-1)',
      textWrap: 'balance'
    }
  }, title), subtitle && /*#__PURE__*/React.createElement("p", {
    style: {
      margin: 0,
      font: 'var(--type-subtitle)',
      color: 'var(--text-secondary)',
      maxWidth: 560,
      textWrap: 'pretty'
    }
  }, subtitle), actions && /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      gap: 'var(--space-sm)',
      marginTop: 'var(--space-md)',
      justifyContent: center ? 'center' : 'flex-start'
    }
  }, actions));
}
Object.assign(__ds_scope, { CtaBanner });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/signature/CtaBanner.jsx", error: String((e && e.message) || e) }); }

// components/signature/GradientStripe.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
const STEPS = ['var(--color-primary)', 'var(--color-block-7)', 'var(--color-glow-500)', 'var(--color-block-6)', 'var(--color-block-5)', 'var(--color-footer-cream)'];
const VERTICAL = 'linear-gradient(180deg, var(--color-primary) 0%, var(--color-glow-800) 25%, var(--color-glow-500) 50%, var(--color-apricot) 75%, var(--color-footer-cream) 100%)';
function GradientStripe({
  variant = 'smooth',
  direction = 'horizontal',
  height = 40,
  style,
  ...rest
}) {
  const base = {
    display: 'flex',
    width: '100%',
    height,
    flexDirection: direction === 'vertical' ? 'column' : 'row',
    ...style
  };
  if (variant === 'stepped') {
    return /*#__PURE__*/React.createElement("div", _extends({
      "aria-hidden": "true",
      style: base
    }, rest), STEPS.map((c, i) => /*#__PURE__*/React.createElement("span", {
      key: i,
      style: {
        flex: '1 1 0',
        background: c
      }
    })));
  }
  return /*#__PURE__*/React.createElement("div", _extends({
    "aria-hidden": "true",
    style: {
      ...base,
      background: direction === 'vertical' ? VERTICAL : 'var(--gradient-stripe)'
    }
  }, rest));
}
Object.assign(__ds_scope, { GradientStripe });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/signature/GradientStripe.jsx", error: String((e && e.message) || e) }); }

// components/signature/HeroBand.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function useWidth(ref) {
  const [w, setW] = React.useState(1280);
  React.useEffect(() => {
    const el = ref.current;
    if (!el || typeof ResizeObserver === 'undefined') return;
    const ro = new ResizeObserver(([e]) => setW(e.contentRect.width));
    ro.observe(el);
    return () => ro.disconnect();
  }, []);
  return w;
}

// Source breakpoints: 84 (≥1280) · 76 (≥1024) · 64 (≥768) · 52 (≥480) · 40
function heroType(w) {
  if (w >= 1280) return [84, -1.5];
  if (w >= 1024) return [76, -1.25];
  if (w >= 768) return [64, -1];
  if (w >= 480) return [52, -0.5];
  return [40, -0.5];
}
function HeroBand({
  eyebrow,
  title,
  subtitle,
  actions,
  media,
  align = 'left',
  style,
  ...rest
}) {
  const ref = React.useRef(null);
  const w = useWidth(ref);
  const [size, track] = heroType(w);
  const stacked = w < 1024;
  const padY = w >= 1024 ? 'var(--space-hero)' : w >= 768 ? 'var(--space-section-lg)' : 'var(--space-section)';
  const centered = align === 'center' && !media;
  return /*#__PURE__*/React.createElement("section", _extends({
    ref: ref,
    style: {
      background: 'var(--gradient-hero)',
      color: 'var(--text-primary)',
      padding: `${padY} var(--container-gutter)`,
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("div", {
    style: {
      maxWidth: 'var(--container-max)',
      margin: '0 auto',
      display: 'grid',
      gridTemplateColumns: media && !stacked ? 'minmax(0, 1fr) minmax(0, 1fr)' : 'minmax(0, 1fr)',
      gap: stacked ? 'var(--space-section-sm)' : 'var(--space-section)',
      alignItems: 'center'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: centered ? 'center' : 'flex-start',
      textAlign: centered ? 'center' : 'left',
      maxWidth: media ? undefined : 760,
      margin: centered ? '0 auto' : undefined
    }
  }, eyebrow && /*#__PURE__*/React.createElement("div", {
    style: {
      marginBottom: 'var(--space-xl)',
      font: 'var(--type-micro-uppercase)',
      letterSpacing: 'var(--tracking-micro-uppercase)',
      textTransform: 'uppercase',
      color: 'var(--text-primary)'
    }
  }, eyebrow), /*#__PURE__*/React.createElement("h1", {
    style: {
      margin: 0,
      font: `400 ${size}px/1.05 var(--font-display)`,
      letterSpacing: track,
      textWrap: 'balance'
    }
  }, title), subtitle && /*#__PURE__*/React.createElement("p", {
    style: {
      margin: 'var(--space-xl) 0 0',
      font: 'var(--type-subtitle)',
      color: 'var(--text-hero)',
      maxWidth: 560,
      textWrap: 'pretty'
    }
  }, subtitle), actions && /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      gap: 'var(--space-sm)',
      marginTop: 'var(--space-xxxl)',
      justifyContent: centered ? 'center' : 'flex-start'
    }
  }, actions)), media && /*#__PURE__*/React.createElement("div", {
    style: {
      minWidth: 0
    }
  }, media)));
}
Object.assign(__ds_scope, { HeroBand });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/signature/HeroBand.jsx", error: String((e && e.message) || e) }); }

// components/stats/LogoWall.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function LogoWall({
  names = [],
  caption,
  minItemWidth = 140,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("div", _extends({
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-md)',
      alignItems: 'center',
      ...style
    }
  }, rest), caption && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)',
      textAlign: 'center'
    }
  }, caption), /*#__PURE__*/React.createElement("ul", {
    style: {
      listStyle: 'none',
      margin: 0,
      padding: 0,
      width: '100%',
      display: 'grid',
      gridTemplateColumns: `repeat(auto-fit, minmax(${minItemWidth}px, 1fr))`
    }
  }, names.map(n => /*#__PURE__*/React.createElement("li", {
    key: n,
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      minHeight: 64,
      padding: 'var(--space-lg)',
      font: 'var(--type-body-md-medium)',
      color: 'var(--text-tertiary)',
      whiteSpace: 'nowrap'
    }
  }, n))));
}
Object.assign(__ds_scope, { LogoWall });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/stats/LogoWall.jsx", error: String((e && e.message) || e) }); }

// components/stats/StatCell.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function StatCell({
  value,
  label,
  divider = false,
  align = 'left',
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("div", _extends({
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-xs)',
      alignItems: align === 'center' ? 'center' : 'flex-start',
      textAlign: align,
      minWidth: 0,
      padding: 'var(--space-lg)',
      borderLeft: divider ? 'var(--border-width) solid var(--border-subtle)' : undefined,
      background: 'transparent',
      color: 'var(--text-primary)',
      ...style
    }
  }, rest), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-stat-display)',
      letterSpacing: 'var(--tracking-stat-display)'
    }
  }, value), label && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)',
      textWrap: 'pretty'
    }
  }, label));
}
Object.assign(__ds_scope, { StatCell });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/stats/StatCell.jsx", error: String((e && e.message) || e) }); }

// components/tabs/PillTabs.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function norm(items) {
  return items.map(it => typeof it === 'string' ? {
    value: it,
    label: it
  } : it);
}
function PillTabs({
  items = [],
  value,
  defaultValue,
  onChange,
  ariaLabel = 'Filter',
  style,
  ...rest
}) {
  const list = norm(items);
  const [inner, setInner] = React.useState(defaultValue ?? (list[0] && list[0].value));
  const current = value !== undefined ? value : inner;
  const select = v => {
    if (value === undefined) setInner(v);
    onChange && onChange(v);
  };
  return /*#__PURE__*/React.createElement("div", _extends({
    role: "tablist",
    "aria-label": ariaLabel,
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      gap: 'var(--space-xs)',
      ...style
    }
  }, rest), list.map(it => {
    const active = it.value === current;
    return /*#__PURE__*/React.createElement("button", {
      key: it.value,
      role: "tab",
      type: "button",
      "aria-selected": active,
      onClick: () => select(it.value),
      style: {
        display: 'inline-flex',
        alignItems: 'center',
        gap: 6,
        padding: 'var(--space-xs) var(--space-md)',
        borderRadius: 'var(--radius-full)',
        border: 'var(--border-width) solid ' + (active ? 'var(--border-inverse)' : 'var(--border-default)'),
        background: active ? 'var(--surface-inverse)' : 'var(--surface-card)',
        color: active ? 'var(--text-inverse)' : 'var(--text-tertiary)',
        font: 'var(--type-body-sm-medium)',
        cursor: 'pointer',
        whiteSpace: 'nowrap',
        transition: 'background-color var(--duration-fast) var(--ease-standard), color var(--duration-fast) var(--ease-standard), border-color var(--duration-fast) var(--ease-standard)'
      }
    }, it.label, it.count != null && /*#__PURE__*/React.createElement("span", {
      style: {
        font: 'var(--type-micro)',
        opacity: active ? 1 : 0.9
      }
    }, it.count));
  }));
}
Object.assign(__ds_scope, { PillTabs });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/tabs/PillTabs.jsx", error: String((e && e.message) || e) }); }

// components/tabs/SegmentedTabs.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function norm(items) {
  return items.map(it => typeof it === 'string' ? {
    value: it,
    label: it
  } : it);
}
function SegmentedTabs({
  items = [],
  value,
  defaultValue,
  onChange,
  fullWidth = false,
  ariaLabel = 'Sections',
  style,
  ...rest
}) {
  const list = norm(items);
  const [inner, setInner] = React.useState(defaultValue ?? (list[0] && list[0].value));
  const current = value !== undefined ? value : inner;
  const select = v => {
    if (value === undefined) setInner(v);
    onChange && onChange(v);
  };
  return /*#__PURE__*/React.createElement("div", _extends({
    role: "tablist",
    "aria-label": ariaLabel,
    style: {
      display: 'flex',
      gap: 'var(--space-xxs)',
      boxShadow: 'inset 0 calc(-1 * var(--border-width)) 0 var(--border-subtle)',
      overflowX: 'auto',
      scrollbarWidth: 'none',
      ...style
    }
  }, rest), list.map(it => {
    const active = it.value === current;
    return /*#__PURE__*/React.createElement("button", {
      key: it.value,
      role: "tab",
      type: "button",
      "aria-selected": active,
      onClick: () => select(it.value),
      style: {
        position: 'relative',
        flex: fullWidth ? '1 1 0' : '0 0 auto',
        padding: 'var(--space-sm) var(--space-md)',
        border: 0,
        background: 'transparent',
        color: active ? 'var(--text-accent)' : 'var(--text-tertiary)',
        font: 'var(--type-body-sm-medium)',
        cursor: 'pointer',
        whiteSpace: 'nowrap',
        transition: 'color var(--duration-fast) var(--ease-standard)'
      }
    }, it.label, /*#__PURE__*/React.createElement("span", {
      "aria-hidden": "true",
      style: {
        position: 'absolute',
        left: 'var(--space-md)',
        right: 'var(--space-md)',
        bottom: 0,
        height: 'var(--border-width-strong)',
        borderRadius: 'var(--radius-full)',
        background: active ? 'var(--accent)' : 'transparent',
        transition: 'background-color var(--duration-fast) var(--ease-standard)'
      }
    }));
  }));
}
Object.assign(__ds_scope, { SegmentedTabs });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/tabs/SegmentedTabs.jsx", error: String((e && e.message) || e) }); }

// components/cooking/Ingredient.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function Ingredient({
  children,
  name,
  amount,
  note,
  tips,
  defaultOpen = false,
  defaultTab = 'amount',
  style,
  ...rest
}) {
  const [open, setOpen] = React.useState(defaultOpen);
  const tipList = tips == null || tips === '' ? [] : Array.isArray(tips) ? tips : [tips];
  const hasTips = tipList.length > 0;
  const [tab, setTab] = React.useState(hasTips ? defaultTab : 'amount');
  const [pos, setPos] = React.useState(null);
  const btnRef = React.useRef(null);
  const popRef = React.useRef(null);
  const autoId = React.useId ? React.useId() : 'ingredient';
  const childText = React.Children.toArray(children).filter(c => typeof c === 'string' || typeof c === 'number').join('').trim();
  const label = name || childText || 'Ingredient';
  const place = React.useCallback(() => {
    const b = btnRef.current;
    const p = popRef.current;
    if (!b || !p) return;
    const r = b.getClientRects()[0] || b.getBoundingClientRect();
    // Clamp to the nearest clipping ancestor (e.g. a device frame or scroll pane), else the viewport.
    let box = {
      left: 0,
      top: 0,
      right: document.documentElement.clientWidth,
      bottom: window.innerHeight
    };
    for (let el = b.parentElement; el && el !== document.body; el = el.parentElement) {
      const cs = getComputedStyle(el);
      if (cs.overflowX !== 'visible' || cs.overflowY !== 'visible') {
        const c = el.getBoundingClientRect();
        box = {
          left: Math.max(box.left, c.left),
          top: Math.max(box.top, c.top),
          right: Math.min(box.right, c.right),
          bottom: Math.min(box.bottom, c.bottom)
        };
        break;
      }
    }
    const w = Math.min(p.offsetWidth, box.right - box.left - 24);
    const h = p.offsetHeight;
    const m = 12;
    const gap = 10;
    const left = Math.min(Math.max(r.left + r.width / 2 - w / 2, box.left + m), Math.max(box.left + m, box.right - w - m));
    let top = r.bottom + gap;
    let below = true;
    if (top + h > box.bottom - m && r.top - gap - h > box.top + m) {
      top = r.top - gap - h;
      below = false;
    }
    setPos({
      left,
      top,
      below,
      width: w,
      arrow: Math.min(Math.max(r.left + r.width / 2 - left, 20), w - 20)
    });
  }, []);
  React.useLayoutEffect(() => {
    if (open) place();else setPos(null);
  }, [open, tab, place]);
  React.useEffect(() => {
    if (!open) return undefined;
    const onDown = e => {
      if (popRef.current && popRef.current.contains(e.target) || btnRef.current && btnRef.current.contains(e.target)) return;
      setOpen(false);
    };
    const onKey = e => {
      if (e.key === 'Escape') {
        setOpen(false);
        btnRef.current && btnRef.current.focus();
      }
    };
    document.addEventListener('pointerdown', onDown);
    document.addEventListener('keydown', onKey);
    window.addEventListener('scroll', place, true);
    window.addEventListener('resize', place);
    return () => {
      document.removeEventListener('pointerdown', onDown);
      document.removeEventListener('keydown', onKey);
      window.removeEventListener('scroll', place, true);
      window.removeEventListener('resize', place);
    };
  }, [open, place]);
  const arrowSide = pos && pos.below ? {
    top: -8,
    borderTop: 'var(--border-width) solid var(--border-strong)',
    borderLeft: 'var(--border-width) solid var(--border-strong)'
  } : {
    bottom: -8,
    borderBottom: 'var(--border-width) solid var(--border-strong)',
    borderRight: 'var(--border-width) solid var(--border-strong)'
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("button", _extends({
    ref: btnRef,
    type: "button",
    "aria-expanded": open,
    "aria-haspopup": "dialog",
    "aria-controls": open ? autoId : undefined,
    onClick: () => setOpen(o => !o),
    style: {
      display: 'inline',
      margin: 0,
      padding: '1px 2px',
      border: 0,
      borderRadius: 'var(--radius-xs)',
      background: open ? 'var(--surface-warm-strong)' : 'transparent',
      font: 'inherit',
      fontWeight: 'var(--weight-medium)',
      color: 'var(--text-primary)',
      textAlign: 'inherit',
      textDecorationLine: 'underline',
      textDecorationColor: 'var(--accent)',
      textDecorationThickness: 2,
      textUnderlineOffset: 4,
      textDecorationSkipInk: 'none',
      cursor: 'pointer',
      WebkitTapHighlightColor: 'transparent',
      transition: 'background-color var(--duration-fast) var(--ease-standard)',
      ...style
    }
  }, rest), children), open && /*#__PURE__*/React.createElement("span", {
    ref: popRef,
    id: autoId,
    role: "dialog",
    "aria-label": label,
    style: {
      position: 'fixed',
      zIndex: 50,
      left: pos ? pos.left : 0,
      top: pos ? pos.top : 0,
      visibility: pos ? 'visible' : 'hidden',
      display: 'block',
      width: pos && pos.width ? pos.width : 'min(300px, calc(100vw - 24px))',
      boxSizing: 'border-box',
      background: 'var(--surface-card)',
      border: 'var(--border-width) solid var(--border-strong)',
      borderRadius: 'var(--radius-lg)',
      boxShadow: 'var(--shadow-4)',
      color: 'var(--text-primary)',
      font: 'var(--type-body-md)',
      textAlign: 'left',
      whiteSpace: 'normal'
    }
  }, pos && /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      position: 'absolute',
      left: pos.arrow - 7,
      width: 14,
      height: 14,
      background: 'var(--surface-card)',
      borderTopLeftRadius: 3,
      borderBottomRightRadius: 3,
      transform: 'rotate(45deg)',
      ...arrowSide
    }
  }), /*#__PURE__*/React.createElement("span", {
    style: {
      position: 'relative',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: 'var(--space-xs)',
      padding: '6px 6px 0 var(--space-md)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-heading-5)',
      minWidth: 0
    }
  }, label.charAt(0).toUpperCase() + label.slice(1)), /*#__PURE__*/React.createElement("button", {
    type: "button",
    "aria-label": "Close",
    onClick: () => {
      setOpen(false);
      btnRef.current && btnRef.current.focus();
    },
    style: {
      display: 'grid',
      placeItems: 'center',
      width: 40,
      height: 40,
      padding: 0,
      border: 0,
      borderRadius: 'var(--radius-md)',
      background: 'transparent',
      color: 'var(--text-tertiary)',
      cursor: 'pointer'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "x",
    size: 18
  }))), hasTips && /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'block',
      padding: '0 var(--space-xs)'
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.SegmentedTabs, {
    fullWidth: true,
    ariaLabel: label + ' details',
    items: [{
      value: 'amount',
      label: 'Amount'
    }, {
      value: 'tips',
      label: 'Tips'
    }],
    value: tab,
    onChange: setTab
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'block',
      padding: hasTips ? 'var(--space-md)' : 'var(--space-xxs) var(--space-md) var(--space-md)'
    }
  }, tab === 'amount' ? /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 4
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-stat-display)',
      fontSize: 40,
      letterSpacing: 'var(--tracking-stat-display)'
    }
  }, amount || 'To taste'), note && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)'
    }
  }, note)) : /*#__PURE__*/React.createElement("span", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)'
    }
  }, tipList.map((t, i) => /*#__PURE__*/React.createElement("span", {
    key: i,
    style: {
      display: 'flex',
      gap: 10,
      alignItems: 'flex-start',
      font: 'var(--type-body-sm)',
      color: 'var(--text-secondary)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      color: 'var(--text-accent)',
      paddingTop: 1
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "lightbulb",
    size: 18
  })), /*#__PURE__*/React.createElement("span", null, t)))))));
}
Object.assign(__ds_scope, { Ingredient });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/cooking/Ingredient.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/App.jsx
try { (() => {
// One phone: tab state, navigation, shared shopping list. `initial` seeds the route (used by screens.html).
function RecipeApp({
  initial = {},
  initialTheme
}) {
  const [theme, setTheme] = React.useState(initialTheme || new URLSearchParams(location.search).get('theme') || 'light');
  const [route, setRoute] = React.useState({
    tab: 'recipes',
    view: 'list',
    recipeId: 'tagliatelle',
    step: 0,
    ...initial
  });
  const [items, setItems] = React.useState(() => {
    const base = window.RA_START_LIST.slice();
    if (initial.prefillList) {
      window.RA_RECIPES[0].ingredients.forEach((i, k) => base.push({
        id: 'p' + k,
        name: i.name,
        amount: i.amount,
        category: i.category,
        checked: k === 1
      }));
    }
    return base;
  });
  const scrollRef = React.useRef(null);
  const go = patch => {
    setRoute(r => ({
      ...r,
      ...patch
    }));
    if (scrollRef.current) scrollRef.current.scrollTop = 0;
  };
  const addToList = recipe => {
    const have = new Set(items.map(s => s.name.toLowerCase()));
    const add = recipe.ingredients.filter(i => !have.has(i.name.toLowerCase())).map(i => ({
      id: recipe.id + '-' + i.id,
      name: i.name,
      amount: i.amount,
      category: i.category,
      note: recipe.title
    }));
    setItems([...items, ...add]);
    return add.length;
  };
  const recipe = window.RA_RECIPES.find(r => r.id === route.recipeId) || window.RA_RECIPES[0];
  const cooking = route.tab === 'recipes' && route.view === 'cook';
  const left = items.filter(i => !i.checked).length;
  let screen;
  if (route.tab === 'shopping') screen = /*#__PURE__*/React.createElement(window.ShoppingScreen, {
    items: items,
    setItems: setItems
  });else if (route.view === 'recipe') screen = /*#__PURE__*/React.createElement(window.RecipeScreen, {
    key: recipe.id,
    recipe: recipe,
    go: go,
    onAddToList: addToList,
    shopping: items
  });else if (cooking) screen = /*#__PURE__*/React.createElement(window.CookingScreen, {
    recipe: recipe,
    step: route.step,
    go: go
  });else screen = /*#__PURE__*/React.createElement(window.RecipesScreen, {
    go: go,
    theme: theme,
    setTheme: setTheme
  });
  return /*#__PURE__*/React.createElement(window.IOSDevice, {
    dark: theme === 'dark'
  }, /*#__PURE__*/React.createElement("div", {
    "data-theme": theme,
    style: {
      height: '100%',
      display: 'flex',
      flexDirection: 'column',
      background: 'var(--surface-page)',
      color: 'var(--text-primary)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    ref: scrollRef,
    style: {
      flex: 1,
      minHeight: 0,
      overflowY: 'auto',
      scrollbarWidth: 'none'
    }
  }, screen), !cooking && /*#__PURE__*/React.createElement(window.RATabBar, {
    tab: route.tab,
    count: left,
    onTab: t => go(t === 'recipes' && route.tab === 'recipes' ? {
      tab: t,
      view: 'list'
    } : {
      tab: t
    })
  })));
}
window.RecipeApp = RecipeApp;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/App.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/CookingScreen.jsx
try { (() => {
function CookingScreen({
  recipe,
  step,
  go
}) {
  const {
    CookingStep,
    Button,
    Icon
  } = window.RA;
  const {
    RAIconButton,
    RAStepBody
  } = window;
  const total = recipe.steps.length;
  const s = recipe.steps[step];
  const next = recipe.steps[step + 1];
  const last = step === total - 1;
  const [finished, setFinished] = React.useState(false);
  if (finished) {
    return /*#__PURE__*/React.createElement("div", {
      style: {
        minHeight: '100%',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        gap: 'var(--space-lg)',
        padding: '64px 24px 48px',
        background: 'var(--gradient-hero)'
      }
    }, /*#__PURE__*/React.createElement("span", {
      style: {
        color: 'var(--text-primary)'
      }
    }, /*#__PURE__*/React.createElement(Icon, {
      name: "circle-check",
      size: 40
    })), /*#__PURE__*/React.createElement("h1", {
      style: {
        margin: 0,
        font: '400 52px/1.05 var(--font-display)',
        letterSpacing: '-0.5px'
      }
    }, "Enjoy your meal."), /*#__PURE__*/React.createElement("p", {
      style: {
        margin: 0,
        font: 'var(--type-subtitle)',
        color: 'var(--text-hero)'
      }
    }, recipe.title, ", ", recipe.servings, " servings."), /*#__PURE__*/React.createElement("div", {
      style: {
        display: 'flex',
        flexDirection: 'column',
        gap: 'var(--space-sm)',
        marginTop: 'var(--space-lg)'
      }
    }, /*#__PURE__*/React.createElement(Button, {
      variant: "dark",
      fullWidth: true,
      onClick: () => go({
        view: 'list'
      }),
      style: {
        minHeight: 48
      }
    }, "Back to recipes"), /*#__PURE__*/React.createElement(Button, {
      variant: "secondary",
      fullWidth: true,
      onClick: () => go({
        view: 'recipe'
      }),
      style: {
        minHeight: 48
      }
    }, "View recipe")));
  }
  return /*#__PURE__*/React.createElement("div", {
    style: {
      minHeight: '100%',
      display: 'flex',
      flexDirection: 'column'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      padding: '60px 16px 16px',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-md)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 12
    }
  }, /*#__PURE__*/React.createElement(RAIconButton, {
    icon: "x",
    label: "Stop cooking",
    onClick: () => go({
      view: 'recipe'
    })
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      minWidth: 0,
      display: 'flex',
      flexDirection: 'column'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm-medium)',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
      whiteSpace: 'nowrap'
    }
  }, recipe.title), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-caption)',
      color: 'var(--text-tertiary)'
    }
  }, "Step ", step + 1, " of ", total))), /*#__PURE__*/React.createElement("div", {
    "aria-hidden": "true",
    style: {
      display: 'grid',
      gridTemplateColumns: `repeat(${total}, 1fr)`,
      gap: 6
    }
  }, recipe.steps.map((_, k) => /*#__PURE__*/React.createElement("span", {
    key: k,
    style: {
      height: 4,
      borderRadius: 'var(--radius-full)',
      background: k <= step ? 'var(--accent)' : 'var(--border-subtle)'
    }
  })))), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      padding: '8px 16px 24px',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-lg)'
    }
  }, /*#__PURE__*/React.createElement(CookingStep, {
    key: step,
    number: step + 1,
    title: s.title,
    duration: s.duration,
    active: true
  }, /*#__PURE__*/React.createElement(RAStepBody, {
    recipe: recipe,
    body: s.body
  })), /*#__PURE__*/React.createElement("p", {
    style: {
      margin: 0,
      display: 'flex',
      alignItems: 'center',
      gap: 8,
      font: 'var(--type-caption)',
      color: 'var(--text-tertiary)'
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "lightbulb",
    size: 16
  }), "Tap an underlined ingredient for its amount and tips."), next && /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 4,
      padding: 'var(--space-md)',
      borderRadius: 'var(--radius-card)',
      border: 'var(--border-width) dashed var(--border-default)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-micro-uppercase)',
      letterSpacing: 'var(--tracking-micro-uppercase)',
      textTransform: 'uppercase',
      color: 'var(--text-tertiary)'
    }
  }, "Up next"), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-md-medium)',
      color: 'var(--text-secondary)'
    }
  }, next.title))), /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'sticky',
      bottom: 0,
      display: 'grid',
      gridTemplateColumns: '1fr 2fr',
      gap: 'var(--space-sm)',
      padding: '12px 16px 34px',
      background: 'var(--surface-page)',
      borderTop: 'var(--border-width) solid var(--border-subtle)'
    }
  }, /*#__PURE__*/React.createElement(Button, {
    variant: "secondary",
    iconLeft: "arrow-left",
    disabled: step === 0,
    onClick: () => go({
      step: step - 1
    }),
    style: {
      minHeight: 48
    }
  }, "Back"), last ? /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    iconRight: "check",
    onClick: () => setFinished(true),
    style: {
      minHeight: 48
    }
  }, "Finish") : /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    iconRight: "arrow-right",
    onClick: () => go({
      step: step + 1
    }),
    style: {
      minHeight: 48
    }
  }, "Next step")));
}
window.CookingScreen = CookingScreen;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/CookingScreen.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/RecipeScreen.jsx
try { (() => {
function RecipeScreen({
  recipe,
  go,
  onAddToList,
  shopping
}) {
  const {
    SegmentedTabs,
    Card,
    Button,
    CookingStep,
    Icon
  } = window.RA;
  const {
    RAIconButton,
    RAMeta,
    RAStepBody
  } = window;
  const [tab, setTab] = React.useState('ingredients');
  const [added, setAdded] = React.useState(null);
  const have = new Set(shopping.map(s => s.name.toLowerCase()));
  const missing = recipe.ingredients.filter(i => !have.has(i.name.toLowerCase()));
  const add = () => {
    const n = onAddToList(recipe);
    setAdded(n);
  };
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      minHeight: '100%'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      background: 'var(--gradient-hero)',
      padding: '60px 16px 24px',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-lg)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      justifyContent: 'space-between'
    }
  }, /*#__PURE__*/React.createElement(RAIconButton, {
    icon: "arrow-left",
    label: "Back to recipes",
    onClick: () => go({
      view: 'list'
    })
  }), /*#__PURE__*/React.createElement(RAIconButton, {
    icon: "bookmark",
    label: "Save recipe"
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-sm)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-micro-uppercase)',
      letterSpacing: 'var(--tracking-micro-uppercase)',
      textTransform: 'uppercase',
      color: 'var(--text-primary)'
    }
  }, recipe.category), /*#__PURE__*/React.createElement("h1", {
    style: {
      margin: 0,
      font: '400 40px/1.05 var(--font-display)',
      letterSpacing: '-0.5px',
      textWrap: 'balance'
    }
  }, recipe.title), /*#__PURE__*/React.createElement("p", {
    style: {
      margin: 0,
      font: 'var(--type-body-md)',
      color: 'var(--text-hero)'
    }
  }, recipe.summary), /*#__PURE__*/React.createElement(RAMeta, {
    recipe: recipe,
    size: "md"
  }))), /*#__PURE__*/React.createElement("div", {
    style: {
      padding: '0 16px',
      position: 'sticky',
      top: 0,
      zIndex: 5,
      background: 'var(--surface-page)'
    }
  }, /*#__PURE__*/React.createElement(SegmentedTabs, {
    fullWidth: true,
    items: [{
      value: 'ingredients',
      label: `Ingredients · ${recipe.ingredients.length}`
    }, {
      value: 'steps',
      label: `Steps · ${recipe.steps.length}`
    }],
    value: tab,
    onChange: setTab
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      padding: '16px 16px 24px',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-md)'
    }
  }, tab === 'ingredients' ? /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement(Card, {
    padding: 0,
    style: {
      overflow: 'hidden'
    }
  }, recipe.ingredients.map((i, k) => /*#__PURE__*/React.createElement("div", {
    key: i.id,
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 12,
      minHeight: 52,
      padding: '10px 16px',
      borderTop: k ? 'var(--border-width) solid var(--border-subtle)' : 0
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      flex: 1,
      minWidth: 0,
      display: 'flex',
      flexDirection: 'column',
      gap: 2
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-md)'
    }
  }, i.name), i.note && /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-caption)',
      color: 'var(--text-tertiary)'
    }
  }, i.note)), /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-body-sm-medium)',
      color: 'var(--text-secondary)',
      whiteSpace: 'nowrap'
    }
  }, i.amount)))), added != null ? /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 10,
      padding: '12px 16px',
      borderRadius: 'var(--radius-card)',
      background: 'var(--surface-warm)',
      border: 'var(--border-width) solid var(--border-warm)',
      font: 'var(--type-body-sm)'
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      color: 'var(--text-accent)'
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "circle-check",
    size: 20
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      flex: 1
    }
  }, added ? `Added ${added} item${added > 1 ? 's' : ''} to your list.` : 'Everything is already on your list.'), /*#__PURE__*/React.createElement(Button, {
    variant: "link",
    onClick: () => go({
      tab: 'shopping'
    })
  }, "View")) : /*#__PURE__*/React.createElement(Button, {
    variant: "secondary",
    iconLeft: "shopping-cart",
    fullWidth: true,
    onClick: add,
    disabled: missing.length === 0
  }, missing.length ? `Add ${missing.length} to shopping list` : 'All on your shopping list')) : /*#__PURE__*/React.createElement("div", null, recipe.steps.map((s, k) => /*#__PURE__*/React.createElement(CookingStep, {
    key: k,
    variant: "plain",
    number: k + 1,
    title: s.title,
    duration: s.duration
  }, /*#__PURE__*/React.createElement(RAStepBody, {
    recipe: recipe,
    body: s.body
  }))))), /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'sticky',
      bottom: 0,
      padding: '12px 16px',
      background: 'var(--surface-page)',
      borderTop: 'var(--border-width) solid var(--border-subtle)'
    }
  }, /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    iconLeft: "play",
    fullWidth: true,
    onClick: () => go({
      view: 'cook',
      step: 0
    }),
    style: {
      minHeight: 48
    }
  }, "Start cooking")));
}
window.RecipeScreen = RecipeScreen;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/RecipeScreen.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/RecipesScreen.jsx
try { (() => {
function RecipesScreen({
  go,
  theme,
  setTheme
}) {
  const {
    TextInput,
    PillTabs,
    Card,
    Badge
  } = window.RA;
  const {
    RAIconButton,
    RAArt,
    RAMeta
  } = window;
  const [q, setQ] = React.useState('');
  const [cat, setCat] = React.useState('All');
  const cats = ['All', ...Array.from(new Set(window.RA_RECIPES.map(r => r.category)))];
  const list = window.RA_RECIPES.filter(r => (cat === 'All' || r.category === cat) && r.title.toLowerCase().includes(q.trim().toLowerCase()));
  return /*#__PURE__*/React.createElement("div", {
    style: {
      padding: '64px 16px 24px',
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-lg)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'flex-start',
      justifyContent: 'space-between',
      gap: 12
    }
  }, /*#__PURE__*/React.createElement("h1", {
    style: {
      margin: 0,
      font: '400 40px/1.05 var(--font-display)',
      letterSpacing: '-0.5px',
      textWrap: 'balance'
    }
  }, "What are we cooking?"), /*#__PURE__*/React.createElement(RAIconButton, {
    icon: theme === 'dark' ? 'sun' : 'moon',
    label: theme === 'dark' ? 'Light theme' : 'OLED dark theme',
    onClick: () => setTheme(theme === 'dark' ? 'light' : 'dark')
  })), /*#__PURE__*/React.createElement(TextInput, {
    iconLeft: "search",
    placeholder: "Search recipes",
    "aria-label": "Search recipes",
    value: q,
    onChange: e => setQ(e.target.value)
  }), /*#__PURE__*/React.createElement(PillTabs, {
    items: cats,
    value: cat,
    onChange: setCat,
    ariaLabel: "Category",
    style: {
      flexWrap: 'nowrap',
      overflowX: 'auto',
      margin: '0 -16px',
      padding: '0 16px',
      scrollbarWidth: 'none'
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 'var(--space-md)'
    }
  }, list.map(r => /*#__PURE__*/React.createElement(Card, {
    key: r.id,
    as: "button",
    padding: 0,
    onClick: () => go({
      view: 'recipe',
      recipeId: r.id
    }),
    style: {
      overflow: 'hidden',
      textAlign: 'left',
      cursor: 'pointer',
      font: 'inherit',
      width: '100%',
      WebkitTapHighlightColor: 'transparent'
    }
  }, /*#__PURE__*/React.createElement(RAArt, {
    icon: r.icon
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 8,
      padding: 'var(--space-md)',
      borderTop: 'var(--border-width) solid var(--border-subtle)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: 8
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: 'var(--type-heading-5)',
      color: 'var(--text-primary)'
    }
  }, r.title), /*#__PURE__*/React.createElement(Badge, {
    variant: "cream"
  }, r.category)), /*#__PURE__*/React.createElement(RAMeta, {
    recipe: r
  })))), list.length === 0 && /*#__PURE__*/React.createElement("p", {
    style: {
      margin: 0,
      padding: '24px 0',
      textAlign: 'center',
      font: 'var(--type-body-sm)',
      color: 'var(--text-tertiary)'
    }
  }, "No recipes match \u201C", q, "\u201D.")));
}
window.RecipesScreen = RecipesScreen;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/RecipesScreen.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/ShoppingScreen.jsx
try { (() => {
function ShoppingScreen({
  items,
  setItems
}) {
  const {
    ShoppingList
  } = window.RA;
  return /*#__PURE__*/React.createElement("div", {
    style: {
      padding: '64px 16px 24px'
    }
  }, /*#__PURE__*/React.createElement(ShoppingList, {
    allowAdd: true,
    items: items,
    onChange: setItems
  }));
}
window.ShoppingScreen = ShoppingScreen;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/ShoppingScreen.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/data.jsx
try { (() => {
// Recipe content for the kit. Step bodies mix plain strings with ingredient refs {i: id, t: 'text shown'}.
const RA_RECIPES = [{
  id: 'tagliatelle',
  title: 'Tagliatelle with shallot cream',
  category: 'Pasta',
  time: 30,
  servings: 2,
  difficulty: 'Easy',
  icon: 'chef-hat',
  summary: 'A quick weeknight pasta with a silky shallot and parmesan sauce.',
  ingredients: [{
    id: 't1',
    name: 'Tagliatelle',
    amount: '320 g',
    category: 'Pantry',
    tips: ['Fresh pasta needs only 2–3 minutes.']
  }, {
    id: 't2',
    name: 'Butter',
    amount: '40 g',
    note: 'unsalted, cold',
    category: 'Dairy',
    tips: ['Cold butter whisked in off the heat gives a glossier sauce.', 'Swap for olive oil to keep it dairy-free.']
  }, {
    id: 't3',
    name: 'Shallot',
    amount: '1',
    note: 'finely chopped',
    category: 'Produce',
    tips: ['Soak in cold water for 5 minutes to soften the bite.']
  }, {
    id: 't4',
    name: 'Double cream',
    amount: '200 ml',
    category: 'Dairy'
  }, {
    id: 't5',
    name: 'Parmesan',
    amount: '60 g',
    note: 'finely grated',
    category: 'Dairy',
    tips: ['Grate it fresh; pre-grated cheese can turn grainy.']
  }, {
    id: 't6',
    name: 'Flat-leaf parsley',
    amount: '1 handful',
    note: 'chopped',
    category: 'Produce'
  }],
  steps: [{
    title: 'Boiling the water',
    duration: 600,
    body: ['Bring a large pot of salted water to the boil. Meanwhile, chop the ', {
      i: 't3',
      t: 'shallot'
    }, ' and grate the ', {
      i: 't5',
      t: 'parmesan'
    }, '.']
  }, {
    title: 'Preparing the sauce',
    duration: 480,
    body: ['Melt half the ', {
      i: 't2',
      t: 'butter'
    }, ' in a wide pan over low heat. Add the ', {
      i: 't3',
      t: 'shallot'
    }, ' and cook until soft but not coloured. Pour in the ', {
      i: 't4',
      t: 'cream'
    }, ' and simmer gently.']
  }, {
    title: 'Cooking the pasta',
    duration: 240,
    body: ['Add the ', {
      i: 't1',
      t: 'tagliatelle'
    }, ' to the boiling water and cook until just tender. Keep a cup of the cooking water.']
  }, {
    title: 'Bringing it together',
    body: ['Toss the pasta into the sauce with the rest of the ', {
      i: 't2',
      t: 'butter'
    }, ' and the ', {
      i: 't5',
      t: 'parmesan'
    }, '. Loosen with a splash of pasta water and finish with the ', {
      i: 't6',
      t: 'parsley'
    }, '.']
  }]
}, {
  id: 'carrots',
  title: 'Roast carrots with tahini',
  category: 'Vegetables',
  time: 45,
  servings: 4,
  difficulty: 'Easy',
  icon: 'carrot',
  summary: 'Sweet roasted carrots with cumin, a lemony tahini dressing and a little honey.',
  ingredients: [{
    id: 'c1',
    name: 'Carrots',
    amount: '800 g',
    note: 'halved lengthways',
    category: 'Produce'
  }, {
    id: 'c2',
    name: 'Olive oil',
    amount: '3 tbsp',
    category: 'Pantry'
  }, {
    id: 'c3',
    name: 'Cumin seeds',
    amount: '1 tsp',
    category: 'Pantry',
    tips: ['Toast them in a dry pan for a minute first for more flavour.']
  }, {
    id: 'c4',
    name: 'Tahini',
    amount: '3 tbsp',
    category: 'Pantry',
    tips: ['Stir the jar well; the oil separates.', 'Add water a spoonful at a time until it pours.']
  }, {
    id: 'c5',
    name: 'Lemon',
    amount: '1',
    note: 'juiced',
    category: 'Produce'
  }, {
    id: 'c6',
    name: 'Honey',
    amount: '1 tsp',
    category: 'Pantry'
  }],
  steps: [{
    title: 'Roasting the carrots',
    duration: 1800,
    body: ['Heat the oven to 220°C. Toss the ', {
      i: 'c1',
      t: 'carrots'
    }, ' with the ', {
      i: 'c2',
      t: 'olive oil'
    }, ' and ', {
      i: 'c3',
      t: 'cumin seeds'
    }, ', spread on a tray and roast until caramelised.']
  }, {
    title: 'Making the dressing',
    body: ['Whisk the ', {
      i: 'c4',
      t: 'tahini'
    }, ' with the ', {
      i: 'c5',
      t: 'lemon juice'
    }, ', a pinch of salt and enough cold water to make it pourable.']
  }, {
    title: 'Serving',
    body: ['Spoon the dressing over the warm carrots and drizzle with the ', {
      i: 'c6',
      t: 'honey'
    }, '.']
  }]
}, {
  id: 'shakshuka',
  title: 'Shakshuka',
  category: 'Breakfast',
  time: 35,
  servings: 2,
  difficulty: 'Medium',
  icon: 'egg',
  summary: 'Eggs poached in a smoky pepper and tomato sauce, finished with feta.',
  ingredients: [{
    id: 's1',
    name: 'Onion',
    amount: '1',
    note: 'sliced',
    category: 'Produce'
  }, {
    id: 's2',
    name: 'Red peppers',
    amount: '2',
    note: 'sliced',
    category: 'Produce'
  }, {
    id: 's3',
    name: 'Garlic',
    amount: '2 cloves',
    category: 'Produce'
  }, {
    id: 's4',
    name: 'Chopped tomatoes',
    amount: '400 g tin',
    category: 'Pantry'
  }, {
    id: 's5',
    name: 'Smoked paprika',
    amount: '1 tsp',
    category: 'Pantry'
  }, {
    id: 's6',
    name: 'Eggs',
    amount: '4',
    category: 'Dairy & eggs',
    tips: ['Crack each egg into a cup first so you can slide it in gently.']
  }, {
    id: 's7',
    name: 'Feta',
    amount: '50 g',
    category: 'Dairy & eggs'
  }],
  steps: [{
    title: 'Softening the vegetables',
    duration: 600,
    body: ['Cook the ', {
      i: 's1',
      t: 'onion'
    }, ' and ', {
      i: 's2',
      t: 'peppers'
    }, ' in a little oil until soft. Add the ', {
      i: 's3',
      t: 'garlic'
    }, ' and ', {
      i: 's5',
      t: 'paprika'
    }, ' for the last minute.']
  }, {
    title: 'Simmering the sauce',
    duration: 600,
    body: ['Pour in the ', {
      i: 's4',
      t: 'tomatoes'
    }, ', season and simmer until thick.']
  }, {
    title: 'Cooking the eggs',
    duration: 420,
    body: ['Make four hollows and crack in the ', {
      i: 's6',
      t: 'eggs'
    }, '. Cover and cook until the whites are set. Crumble over the ', {
      i: 's7',
      t: 'feta'
    }, '.']
  }]
}];
const RA_START_LIST = [{
  id: 'x1',
  name: 'Milk',
  amount: '1 l',
  category: 'Dairy'
}, {
  id: 'x2',
  name: 'Sourdough loaf',
  amount: '1',
  category: 'Bakery'
}, {
  id: 'x3',
  name: 'Coffee beans',
  amount: '250 g',
  category: 'Pantry',
  checked: true
}];
Object.assign(window, {
  RA_RECIPES,
  RA_START_LIST
});
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/data.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/ios-frame.jsx
try { (() => {
// @ds-adherence-ignore -- omelette starter scaffold (raw elements/hex/px by design)
// Copied omelette starter. Re-running copy_starter_component with this kind overwrites this file with the latest version (page content is unaffected).

/* BEGIN USAGE */
// iOS.jsx — Simplified iOS 26 (Liquid Glass) device frame
// Based on the iOS 26 UI Kit + Figma status bar spec. No assets, no deps.
// Exports (to window): IOSDevice, IOSStatusBar, IOSNavBar, IOSGlassPill, IOSList, IOSListRow, IOSKeyboard
//
// Usage — wrap your screen content in <IOSDevice> to get the bezel, status bar
// and home indicator (props: title, dark, keyboard):
//
//   <IOSDevice title="Settings">
//     ...your screen content...
//   </IOSDevice>
//   <IOSDevice dark title="Search" keyboard>…</IOSDevice>
/* END USAGE */

// ─────────────────────────────────────────────────────────────
// Status bar
// ─────────────────────────────────────────────────────────────
function IOSStatusBar({
  dark = false,
  time = '9:41'
}) {
  const c = dark ? '#fff' : '#000';
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      gap: 154,
      alignItems: 'center',
      justifyContent: 'center',
      padding: '21px 24px 19px',
      boxSizing: 'border-box',
      position: 'relative',
      zIndex: 20,
      width: '100%'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      height: 22,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      paddingTop: 1.5
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      fontFamily: '-apple-system, "SF Pro", system-ui',
      fontWeight: 590,
      fontSize: 17,
      lineHeight: '22px',
      color: c
    }
  }, time)), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      height: 22,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      gap: 7,
      paddingTop: 1,
      paddingRight: 1
    }
  }, /*#__PURE__*/React.createElement("svg", {
    width: "19",
    height: "12",
    viewBox: "0 0 19 12"
  }, /*#__PURE__*/React.createElement("rect", {
    x: "0",
    y: "7.5",
    width: "3.2",
    height: "4.5",
    rx: "0.7",
    fill: c
  }), /*#__PURE__*/React.createElement("rect", {
    x: "4.8",
    y: "5",
    width: "3.2",
    height: "7",
    rx: "0.7",
    fill: c
  }), /*#__PURE__*/React.createElement("rect", {
    x: "9.6",
    y: "2.5",
    width: "3.2",
    height: "9.5",
    rx: "0.7",
    fill: c
  }), /*#__PURE__*/React.createElement("rect", {
    x: "14.4",
    y: "0",
    width: "3.2",
    height: "12",
    rx: "0.7",
    fill: c
  })), /*#__PURE__*/React.createElement("svg", {
    width: "17",
    height: "12",
    viewBox: "0 0 17 12"
  }, /*#__PURE__*/React.createElement("path", {
    d: "M8.5 3.2C10.8 3.2 12.9 4.1 14.4 5.6L15.5 4.5C13.7 2.7 11.2 1.5 8.5 1.5C5.8 1.5 3.3 2.7 1.5 4.5L2.6 5.6C4.1 4.1 6.2 3.2 8.5 3.2Z",
    fill: c
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8.5 6.8C9.9 6.8 11.1 7.3 12 8.2L13.1 7.1C11.8 5.9 10.2 5.1 8.5 5.1C6.8 5.1 5.2 5.9 3.9 7.1L5 8.2C5.9 7.3 7.1 6.8 8.5 6.8Z",
    fill: c
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "8.5",
    cy: "10.5",
    r: "1.5",
    fill: c
  })), /*#__PURE__*/React.createElement("svg", {
    width: "27",
    height: "13",
    viewBox: "0 0 27 13"
  }, /*#__PURE__*/React.createElement("rect", {
    x: "0.5",
    y: "0.5",
    width: "23",
    height: "12",
    rx: "3.5",
    stroke: c,
    strokeOpacity: "0.35",
    fill: "none"
  }), /*#__PURE__*/React.createElement("rect", {
    x: "2",
    y: "2",
    width: "20",
    height: "9",
    rx: "2",
    fill: c
  }), /*#__PURE__*/React.createElement("path", {
    d: "M25 4.5V8.5C25.8 8.2 26.5 7.2 26.5 6.5C26.5 5.8 25.8 4.8 25 4.5Z",
    fill: c,
    fillOpacity: "0.4"
  }))));
}

// ─────────────────────────────────────────────────────────────
// Liquid glass pill — blur + tint + shine
// ─────────────────────────────────────────────────────────────
function IOSGlassPill({
  children,
  dark = false,
  style = {}
}) {
  return /*#__PURE__*/React.createElement("div", {
    style: {
      height: 44,
      minWidth: 44,
      borderRadius: 9999,
      position: 'relative',
      overflow: 'hidden',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      boxShadow: dark ? '0 2px 6px rgba(0,0,0,0.35), 0 6px 16px rgba(0,0,0,0.2)' : '0 1px 3px rgba(0,0,0,0.07), 0 3px 10px rgba(0,0,0,0.06)',
      ...style
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'absolute',
      inset: 0,
      borderRadius: 9999,
      backdropFilter: 'blur(12px) saturate(180%)',
      WebkitBackdropFilter: 'blur(12px) saturate(180%)',
      background: dark ? 'rgba(120,120,128,0.28)' : 'rgba(255,255,255,0.5)'
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'absolute',
      inset: 0,
      borderRadius: 9999,
      boxShadow: dark ? 'inset 1.5px 1.5px 1px rgba(255,255,255,0.15), inset -1px -1px 1px rgba(255,255,255,0.08)' : 'inset 1.5px 1.5px 1px rgba(255,255,255,0.7), inset -1px -1px 1px rgba(255,255,255,0.4)',
      border: dark ? '0.5px solid rgba(255,255,255,0.15)' : '0.5px solid rgba(0,0,0,0.06)'
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'relative',
      zIndex: 1,
      display: 'flex',
      alignItems: 'center',
      padding: '0 4px'
    }
  }, children));
}

// ─────────────────────────────────────────────────────────────
// Navigation bar — glass pills + large title
// ─────────────────────────────────────────────────────────────
function IOSNavBar({
  title = 'Title',
  dark = false,
  trailingIcon = true
}) {
  const muted = dark ? 'rgba(255,255,255,0.6)' : '#404040';
  const text = dark ? '#fff' : '#000';
  const pillIcon = content => /*#__PURE__*/React.createElement(IOSGlassPill, {
    dark: dark
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      width: 36,
      height: 36,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }
  }, content));
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 10,
      paddingTop: 62,
      paddingBottom: 10,
      position: 'relative',
      zIndex: 5
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      padding: '0 16px'
    }
  }, pillIcon(/*#__PURE__*/React.createElement("svg", {
    width: "12",
    height: "20",
    viewBox: "0 0 12 20",
    fill: "none",
    style: {
      marginLeft: -1
    }
  }, /*#__PURE__*/React.createElement("path", {
    d: "M10 2L2 10l8 8",
    stroke: muted,
    strokeWidth: "2.5",
    strokeLinecap: "round",
    strokeLinejoin: "round"
  }))), trailingIcon && pillIcon(/*#__PURE__*/React.createElement("svg", {
    width: "22",
    height: "6",
    viewBox: "0 0 22 6"
  }, /*#__PURE__*/React.createElement("circle", {
    cx: "3",
    cy: "3",
    r: "2.5",
    fill: muted
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "11",
    cy: "3",
    r: "2.5",
    fill: muted
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "19",
    cy: "3",
    r: "2.5",
    fill: muted
  })))), /*#__PURE__*/React.createElement("div", {
    style: {
      padding: '0 16px',
      fontFamily: '-apple-system, system-ui',
      fontSize: 34,
      fontWeight: 700,
      lineHeight: '41px',
      color: text,
      letterSpacing: 0.4
    }
  }, title));
}

// ─────────────────────────────────────────────────────────────
// Grouped list (inset card, r:26) + row (52px)
// ─────────────────────────────────────────────────────────────
function IOSListRow({
  title,
  detail,
  icon,
  chevron = true,
  isLast = false,
  dark = false
}) {
  const text = dark ? '#fff' : '#000';
  const sec = dark ? 'rgba(235,235,245,0.6)' : 'rgba(60,60,67,0.6)';
  const ter = dark ? 'rgba(235,235,245,0.3)' : 'rgba(60,60,67,0.3)';
  const sep = dark ? 'rgba(84,84,88,0.65)' : 'rgba(60,60,67,0.12)';
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      alignItems: 'center',
      minHeight: 52,
      padding: '0 16px',
      position: 'relative',
      fontFamily: '-apple-system, system-ui',
      fontSize: 17,
      letterSpacing: -0.43
    }
  }, icon && /*#__PURE__*/React.createElement("div", {
    style: {
      width: 30,
      height: 30,
      borderRadius: 7,
      background: icon,
      marginRight: 12,
      flexShrink: 0
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      color: text
    }
  }, title), detail && /*#__PURE__*/React.createElement("span", {
    style: {
      color: sec,
      marginRight: 6
    }
  }, detail), chevron && /*#__PURE__*/React.createElement("svg", {
    width: "8",
    height: "14",
    viewBox: "0 0 8 14",
    style: {
      flexShrink: 0
    }
  }, /*#__PURE__*/React.createElement("path", {
    d: "M1 1l6 6-6 6",
    stroke: ter,
    strokeWidth: "2",
    fill: "none",
    strokeLinecap: "round",
    strokeLinejoin: "round"
  })), !isLast && /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'absolute',
      bottom: 0,
      right: 0,
      left: icon ? 58 : 16,
      height: 0.5,
      background: sep
    }
  }));
}
function IOSList({
  header,
  children,
  dark = false
}) {
  const hc = dark ? 'rgba(235,235,245,0.6)' : 'rgba(60,60,67,0.6)';
  const bg = dark ? '#1C1C1E' : '#fff';
  return /*#__PURE__*/React.createElement("div", null, header && /*#__PURE__*/React.createElement("div", {
    style: {
      fontFamily: '-apple-system, system-ui',
      fontSize: 13,
      color: hc,
      textTransform: 'uppercase',
      padding: '8px 36px 6px',
      letterSpacing: -0.08
    }
  }, header), /*#__PURE__*/React.createElement("div", {
    style: {
      background: bg,
      borderRadius: 26,
      margin: '0 16px',
      overflow: 'hidden'
    }
  }, children));
}

// ─────────────────────────────────────────────────────────────
// Device frame
// ─────────────────────────────────────────────────────────────
function IOSDevice({
  children,
  width = 402,
  height = 874,
  dark = false,
  title,
  keyboard = false
}) {
  return (
    /*#__PURE__*/
    // data-om-starter: inert presence marker — Claude Design's starter-usage
    // probe reads it; it renders nothing. Keep it on this root element.
    React.createElement("div", {
      "data-om-starter": "ios-frame",
      style: {
        width,
        height,
        borderRadius: 48,
        overflow: 'hidden',
        position: 'relative',
        background: dark ? '#000' : '#F2F2F7',
        boxShadow: '0 40px 80px rgba(0,0,0,0.18), 0 0 0 1px rgba(0,0,0,0.12)',
        fontFamily: '-apple-system, system-ui, sans-serif',
        WebkitFontSmoothing: 'antialiased'
      }
    }, /*#__PURE__*/React.createElement("div", {
      style: {
        position: 'absolute',
        top: 11,
        left: '50%',
        transform: 'translateX(-50%)',
        width: 126,
        height: 37,
        borderRadius: 24,
        background: '#000',
        zIndex: 50
      }
    }), /*#__PURE__*/React.createElement("div", {
      style: {
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        zIndex: 10
      }
    }, /*#__PURE__*/React.createElement(IOSStatusBar, {
      dark: dark
    })), /*#__PURE__*/React.createElement("div", {
      style: {
        height: '100%',
        display: 'flex',
        flexDirection: 'column'
      }
    }, title !== undefined && /*#__PURE__*/React.createElement(IOSNavBar, {
      title: title,
      dark: dark
    }), /*#__PURE__*/React.createElement("div", {
      style: {
        flex: 1,
        overflow: 'auto'
      }
    }, children), keyboard && /*#__PURE__*/React.createElement(IOSKeyboard, {
      dark: dark
    })), /*#__PURE__*/React.createElement("div", {
      style: {
        position: 'absolute',
        bottom: 0,
        left: 0,
        right: 0,
        zIndex: 60,
        height: 34,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'flex-end',
        paddingBottom: 8,
        pointerEvents: 'none'
      }
    }, /*#__PURE__*/React.createElement("div", {
      style: {
        width: 139,
        height: 5,
        borderRadius: 100,
        background: dark ? 'rgba(255,255,255,0.7)' : 'rgba(0,0,0,0.25)'
      }
    })))
  );
}

// ─────────────────────────────────────────────────────────────
// Keyboard — iOS 26 liquid glass
// ─────────────────────────────────────────────────────────────
function IOSKeyboard({
  dark = false
}) {
  const glyph = dark ? 'rgba(255,255,255,0.7)' : '#595959';
  const sugg = dark ? 'rgba(255,255,255,0.6)' : '#333';
  const keyBg = dark ? 'rgba(255,255,255,0.22)' : 'rgba(255,255,255,0.85)';

  // special-key icons
  const icons = {
    shift: /*#__PURE__*/React.createElement("svg", {
      width: "19",
      height: "17",
      viewBox: "0 0 19 17"
    }, /*#__PURE__*/React.createElement("path", {
      d: "M9.5 1L1 9.5h4.5V16h8V9.5H18L9.5 1z",
      fill: glyph
    })),
    del: /*#__PURE__*/React.createElement("svg", {
      width: "23",
      height: "17",
      viewBox: "0 0 23 17"
    }, /*#__PURE__*/React.createElement("path", {
      d: "M7 1h13a2 2 0 012 2v11a2 2 0 01-2 2H7l-6-7.5L7 1z",
      fill: "none",
      stroke: glyph,
      strokeWidth: "1.6",
      strokeLinejoin: "round"
    }), /*#__PURE__*/React.createElement("path", {
      d: "M10 5l7 7M17 5l-7 7",
      stroke: glyph,
      strokeWidth: "1.6",
      strokeLinecap: "round"
    })),
    ret: /*#__PURE__*/React.createElement("svg", {
      width: "20",
      height: "14",
      viewBox: "0 0 20 14"
    }, /*#__PURE__*/React.createElement("path", {
      d: "M18 1v6H4m0 0l4-4M4 7l4 4",
      fill: "none",
      stroke: "#fff",
      strokeWidth: "1.8",
      strokeLinecap: "round",
      strokeLinejoin: "round"
    }))
  };
  const key = (content, {
    w,
    flex,
    ret,
    fs = 25,
    k
  } = {}) => /*#__PURE__*/React.createElement("div", {
    key: k,
    style: {
      height: 42,
      borderRadius: 8.5,
      flex: flex ? 1 : undefined,
      width: w,
      minWidth: 0,
      background: ret ? '#08f' : keyBg,
      boxShadow: '0 1px 0 rgba(0,0,0,0.075)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      fontFamily: '-apple-system, "SF Compact", system-ui',
      fontSize: fs,
      fontWeight: 458,
      color: ret ? '#fff' : glyph
    }
  }, content);
  const row = (keys, pad = 0) => /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      gap: 6.5,
      justifyContent: 'center',
      padding: `0 ${pad}px`
    }
  }, keys.map(l => key(l, {
    flex: true,
    k: l
  })));
  return /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'relative',
      zIndex: 15,
      borderRadius: 27,
      overflow: 'hidden',
      padding: '11px 0 2px',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      boxShadow: dark ? '0 -2px 20px rgba(0,0,0,0.09)' : '0 -1px 6px rgba(0,0,0,0.018), 0 -3px 20px rgba(0,0,0,0.012)'
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'absolute',
      inset: 0,
      borderRadius: 27,
      backdropFilter: 'blur(12px) saturate(180%)',
      WebkitBackdropFilter: 'blur(12px) saturate(180%)',
      background: dark ? 'rgba(120,120,128,0.14)' : 'rgba(255,255,255,0.25)'
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      position: 'absolute',
      inset: 0,
      borderRadius: 27,
      boxShadow: dark ? 'inset 1.5px 1.5px 1px rgba(255,255,255,0.15)' : 'inset 1.5px 1.5px 1px rgba(255,255,255,0.7), inset -1px -1px 1px rgba(255,255,255,0.4)',
      border: dark ? '0.5px solid rgba(255,255,255,0.15)' : '0.5px solid rgba(0,0,0,0.06)',
      pointerEvents: 'none'
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      gap: 20,
      alignItems: 'center',
      padding: '8px 22px 13px',
      width: '100%',
      boxSizing: 'border-box',
      position: 'relative'
    }
  }, ['"The"', 'the', 'to'].map((w, i) => /*#__PURE__*/React.createElement(React.Fragment, {
    key: i
  }, i > 0 && /*#__PURE__*/React.createElement("div", {
    style: {
      width: 1,
      height: 25,
      background: '#ccc',
      opacity: 0.3
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      textAlign: 'center',
      fontFamily: '-apple-system, system-ui',
      fontSize: 17,
      color: sugg,
      letterSpacing: -0.43,
      lineHeight: '22px'
    }
  }, w)))), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 13,
      padding: '0 6.5px',
      width: '100%',
      boxSizing: 'border-box',
      position: 'relative'
    }
  }, row(['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p']), row(['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'], 20), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      gap: 14.25,
      alignItems: 'center'
    }
  }, key(icons.shift, {
    w: 45,
    k: 'shift'
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      gap: 6.5,
      flex: 1
    }
  }, ['z', 'x', 'c', 'v', 'b', 'n', 'm'].map(l => key(l, {
    flex: true,
    k: l
  }))), key(icons.del, {
    w: 45,
    k: 'del'
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      gap: 6,
      alignItems: 'center'
    }
  }, key('ABC', {
    w: 92.25,
    fs: 18,
    k: 'abc'
  }), key('', {
    flex: true,
    k: 'space'
  }), key(icons.ret, {
    w: 92.25,
    ret: true,
    k: 'ret'
  }))), /*#__PURE__*/React.createElement("div", {
    style: {
      height: 56,
      width: '100%',
      position: 'relative'
    }
  }));
}
Object.assign(window, {
  IOSDevice,
  IOSStatusBar,
  IOSNavBar,
  IOSGlassPill,
  IOSList,
  IOSListRow,
  IOSKeyboard
});
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/ios-frame.jsx", error: String((e && e.message) || e) }); }

// ui_kits/recipe-app/parts.jsx
try { (() => {
// Kit-level pieces: app chrome around the DS components.
const RA = window.SubtleGradientDesignSystem_b07db2;
function RAIconButton({
  icon,
  label,
  onClick,
  tone = 'card'
}) {
  const {
    Icon
  } = RA;
  return /*#__PURE__*/React.createElement("button", {
    type: "button",
    "aria-label": label,
    onClick: onClick,
    style: {
      display: 'grid',
      placeItems: 'center',
      width: 44,
      height: 44,
      flex: '0 0 auto',
      padding: 0,
      borderRadius: 'var(--radius-md)',
      border: 'var(--border-width) solid var(--border-default)',
      background: tone === 'card' ? 'var(--surface-card)' : 'transparent',
      color: 'var(--text-primary)',
      cursor: 'pointer',
      WebkitTapHighlightColor: 'transparent'
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: icon,
    size: 20
  }));
}
function RAArt({
  icon,
  height = 132,
  size = 48,
  radius
}) {
  const {
    Icon
  } = RA;
  return /*#__PURE__*/React.createElement("div", {
    style: {
      height,
      display: 'grid',
      placeItems: 'center',
      background: 'var(--gradient-hero)',
      color: 'var(--text-primary)',
      borderRadius: radius
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: icon,
    size: size,
    stroke: 2
  }));
}
function RAMeta({
  recipe,
  size = 'sm'
}) {
  const {
    Icon
  } = RA;
  const items = [['clock', recipe.time + ' min'], ['users', recipe.servings + ' servings'], ['flame', recipe.difficulty]];
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      gap: size === 'sm' ? 12 : 16,
      font: size === 'sm' ? 'var(--type-caption)' : 'var(--type-body-sm)',
      color: 'var(--text-tertiary)'
    }
  }, items.map(([ic, t]) => /*#__PURE__*/React.createElement("span", {
    key: ic,
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 6
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: ic,
    size: size === 'sm' ? 14 : 16
  }), t)));
}
function RATabBar({
  tab,
  onTab,
  count
}) {
  const {
    Icon
  } = RA;
  const tabs = [['recipes', 'utensils', 'Recipes'], ['shopping', 'shopping-basket', 'Shopping list']];
  return /*#__PURE__*/React.createElement("nav", {
    "aria-label": "Main",
    style: {
      flex: '0 0 auto',
      display: 'grid',
      gridTemplateColumns: '1fr 1fr',
      padding: '6px 12px 30px',
      background: 'var(--surface-page)',
      borderTop: 'var(--border-width) solid var(--border-subtle)'
    }
  }, tabs.map(([id, ic, label]) => {
    const on = tab === id;
    return /*#__PURE__*/React.createElement("button", {
      key: id,
      type: "button",
      "aria-current": on ? 'page' : undefined,
      onClick: () => onTab(id),
      style: {
        position: 'relative',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: 4,
        minHeight: 52,
        padding: '6px 0',
        border: 0,
        background: 'transparent',
        color: on ? 'var(--text-accent)' : 'var(--text-tertiary)',
        font: 'var(--type-micro)',
        cursor: 'pointer',
        WebkitTapHighlightColor: 'transparent'
      }
    }, /*#__PURE__*/React.createElement("span", {
      style: {
        position: 'relative'
      }
    }, /*#__PURE__*/React.createElement(Icon, {
      name: ic,
      size: 24
    }), id === 'shopping' && count > 0 && /*#__PURE__*/React.createElement("span", {
      style: {
        position: 'absolute',
        top: -6,
        left: 16,
        minWidth: 18,
        height: 18,
        padding: '0 5px',
        borderRadius: 'var(--radius-full)',
        background: 'var(--accent)',
        color: 'var(--accent-fg)',
        font: '600 11px/18px var(--font-sans)',
        textAlign: 'center',
        border: 'var(--border-width) solid var(--surface-page)',
        boxSizing: 'content-box'
      }
    }, count)), label);
  }));
}
function RAStepBody({
  recipe,
  body
}) {
  const {
    Ingredient
  } = RA;
  const byId = Object.fromEntries(recipe.ingredients.map(i => [i.id, i]));
  return body.map((seg, k) => {
    if (typeof seg === 'string') return /*#__PURE__*/React.createElement(React.Fragment, {
      key: k
    }, seg);
    const ing = byId[seg.i];
    return /*#__PURE__*/React.createElement(Ingredient, {
      key: k,
      name: ing.name,
      amount: ing.amount,
      note: ing.note,
      tips: ing.tips
    }, seg.t);
  });
}
Object.assign(window, {
  RA,
  RAIconButton,
  RAArt,
  RAMeta,
  RATabBar,
  RAStepBody
});
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/recipe-app/parts.jsx", error: String((e && e.message) || e) }); }

__ds_ns.Badge = __ds_scope.Badge;

__ds_ns.PromoBanner = __ds_scope.PromoBanner;

__ds_ns.AppDownloadBadge = __ds_scope.AppDownloadBadge;

__ds_ns.Button = __ds_scope.Button;

__ds_ns.Card = __ds_scope.Card;

__ds_ns.CodeBlock = __ds_scope.CodeBlock;

__ds_ns.FeatureIconTile = __ds_scope.FeatureIconTile;

__ds_ns.IndustryTile = __ds_scope.IndustryTile;

__ds_ns.TestimonialCard = __ds_scope.TestimonialCard;

__ds_ns.CookingStep = __ds_scope.CookingStep;

__ds_ns.Ingredient = __ds_scope.Ingredient;

__ds_ns.StepTimer = __ds_scope.StepTimer;

__ds_ns.FaqItem = __ds_scope.FaqItem;

__ds_ns.Footer = __ds_scope.Footer;

__ds_ns.FooterLink = __ds_scope.FooterLink;

__ds_ns.ContactFormPanel = __ds_scope.ContactFormPanel;

__ds_ns.TextArea = __ds_scope.TextArea;

__ds_ns.TextInput = __ds_scope.TextInput;

__ds_ns.ICON_NAMES = __ds_scope.ICON_NAMES;

__ds_ns.Icon = __ds_scope.Icon;

__ds_ns.TopNav = __ds_scope.TopNav;

__ds_ns.PricingCard = __ds_scope.PricingCard;

__ds_ns.ShoppingList = __ds_scope.ShoppingList;

__ds_ns.ShoppingListItem = __ds_scope.ShoppingListItem;

__ds_ns.CtaBanner = __ds_scope.CtaBanner;

__ds_ns.GradientStripe = __ds_scope.GradientStripe;

__ds_ns.HeroBand = __ds_scope.HeroBand;

__ds_ns.LogoWall = __ds_scope.LogoWall;

__ds_ns.StatCell = __ds_scope.StatCell;

__ds_ns.PillTabs = __ds_scope.PillTabs;

__ds_ns.SegmentedTabs = __ds_scope.SegmentedTabs;

})();
