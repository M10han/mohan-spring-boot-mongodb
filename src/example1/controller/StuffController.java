package controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import domain.Stuff;
import repository.StuffRepository;

@Controller
public class StuffController {
	private static final String VIEW_PATH_STUFF = "pages/stuff";
	private static final String VIEW_PATH_EDIT = "pages/edit";
	private static final String MODEL_ATTR_IS_CREATE = "isCreate";
	private static final String MODEL_ATTR_IS_ERROR = "isError";
	private static final String MODEL_ATTR_LIST_OF_SIZES = "listOfSizes";
	private static final String MODEL_ATTR_STUFF = "stuff";
	
	private final StuffRepository stuffRepository;
	
	@Autowired
	public StuffController(StuffRepository stuffRepository) {
		this.stuffRepository = stuffRepository;
	}
	
	@RequestMapping(value = {"/", "/stuff"}, method = RequestMethod.GET)
	public ModelAndView stuff (@RequestMapping(name="size",required=false) Size size) {
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH_STUFF);
		if(size!=null) {
			modelAndView.addObject("listOfStuff", stuffRepository.findBySize(size));
		} else {
			modelAndView.addObject("listOfStuff", stuffRepository.findAll());
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH_EDIT);
		modelAndView.addObject(MODEL_ATTR_IS_CREATE, true);
		modelAndView.addObject(MODEL_ATTR_STUFF, new Stuff());
		modelAndView.addObject(MODEL_ATTR_LIST_OF_SIZES, Size.values());
		return modelAndView;
	}
	
	@RequestMapping(value = "/submit-create", method = RequestMethod.POST)
    public ModelAndView submitCreate(@Valid @ModelAttribute Stuff stuff, BindingResult bindingResult)
    {
        return handleSubmitEdit(true, stuff, bindingResult);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(name = "id", required = true) String id)
    {
        try {
            Optional<Stuff> stuff = stuffRepository.findById(id);

            if (stuff.isPresent()) {
                ModelAndView modelAndView = new ModelAndView(VIEW_PATH_EDIT);
                modelAndView.addObject(MODEL_ATTR_IS_CREATE, false);
                modelAndView.addObject(MODEL_ATTR_STUFF, stuff.get());
                modelAndView.addObject(MODEL_ATTR_LIST_OF_SIZES, Size.values());
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView(new RedirectView("/stuff"));
                modelAndView.addObject(MODEL_ATTR_IS_ERROR, true);
                return modelAndView;
            }
        } catch (Exception ex) {
            ModelAndView modelAndView = new ModelAndView(new RedirectView("/stuff"));
            modelAndView.addObject(MODEL_ATTR_IS_ERROR, true);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/submit-edit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@Valid @ModelAttribute Stuff stuff, BindingResult bindingResult)
    {
        return handleSubmitEdit(false, stuff, bindingResult);
    }

    private ModelAndView handleSubmitEdit(boolean isCreate, Stuff stuff, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView(VIEW_PATH_EDIT, bindingResult.getModel());
            modelAndView.addObject(MODEL_ATTR_IS_CREATE, isCreate);
            modelAndView.addObject(MODEL_ATTR_LIST_OF_SIZES, Size.values());
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView(new RedirectView("/stuff"));
        try {
            stuffRepository.save(stuff);
        } catch (Exception ex) {
            modelAndView.addObject(MODEL_ATTR_IS_ERROR, true);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/submit-delete", method = RequestMethod.POST)
    public ModelAndView submitDelete(@RequestParam(name = "id", required = true) String id)
    {
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/stuff"));
        try {
            stuffRepository.deleteById(id);
        } catch (Exception ex) {
            modelAndView.addObject(MODEL_ATTR_IS_ERROR, true);
        }
        return modelAndView;
    }
	
}
