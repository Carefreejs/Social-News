package com.laioffer.tinnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.repository.NewsRepository;

//mvvm: model , view , viewModel
// viewModel when UI is real destroyed, then viewModel will be destryed
// when rotate the screen or configuration change UI maybe lost so we need viewModel
public class HomeViewModel extends ViewModel {
    private final NewsRepository newsRepository;

    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    // event
    public void setCountryInput(String country){
        countryInput.setValue(country);
    }
    public LiveData<NewsResponse> getTopHeadlines() {
        return Transformations.switchMap(countryInput, newsRepository::getTopHeadlines);
    }
    public void setFavoriteArticleInput (Article article){
        newsRepository.favoriteArticle(article);
    }
}
