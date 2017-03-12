function getCandidates(self,district) {
  axios
    .get('candidate/' + district + '/district')
    .then(function(response){
      self.setState({
        candidatesList :  response.data,
      });
    })
    .catch(function(err){
      console.error('SingleResultContainer.componentWillMount.axios.get.party', err);
    });
}
function getDistrict(self) {
  axios
    .get('district/representative')
    .then(function(response){
      getCandidates(self,response.data.id);
      self.setState({
        districtInfo :  response.data,
        isLoading : false,
      });
      if (response.data.resultSingleRegistered) {
        self.setState({isVotesRegistered : true});
      }
    })
    .catch(function(err){
      console.error('SingleResultContainer.componentWillMount.axios.get.district', err);
    });
}
function isCountEqualsOrLess(list,max){
  var count = 0;
  for (var key1 in list) {
    if (list.hasOwnProperty(key1)) {
      count = count + parseInt(list[key1]);
    }
  }
  if (count > max) {
    return false;
  } else {
    return true;
  }
}
function isVotesNegativeValue(list) {
  for (var key1 in list) {
    if (list.hasOwnProperty(key1)) {
      if (parseInt(list[key1]) < 0) {
        return true;
      }
    }
  }
  return false;
}

var list = {};
var postArray = [];
var errorMesages = [];
var SingleResultContainer = React.createClass({
  getInitialState: function() {
    return {
      candidatesList : [],
      districtInfo : [],
      isLoading : true,
      isVotesRegistered : false,
      errorMesages : [],
    };
  },
  componentWillMount: function() {
    getDistrict(this);
  },
  registerVotes :function(id,votes){
    list[id]=votes;
  },
  onHandleSpoiledChange : function(event){
    list[-1991]=event.target.value;
  },
  onHandleSubmit : function(event) {
    postArray = [];
    event.preventDefault();
    errorMesages = [];
    this.setState({errorMesages : []});
    if (!isCountEqualsOrLess(list, this.state.districtInfo.numberOfElectors))
      {errorMesages.push('Apygarda ' + this.state.districtInfo.name + ' turi tik ' +  this.state.districtInfo.numberOfElectors + ' balsų.');}
    if (isVotesNegativeValue(list)) { errorMesages.push('Rezultatai negali turėti neigiamų reikšmių');}
    if (errorMesages.length != 0) {
      this.setState({errorMesages : errorMesages});
    } else {
      for (var key in list) {
        if (list.hasOwnProperty(key)) {
          var self = this;
          postArray.push(
            {
              'candidate' : {'id' : key},
              'district' : {'id' : this.state.districtInfo.id},
              'votes' : list[key],
              'datePublished' : new Date(),
            }
          );
        }
      }
      axios
      .post('/result-single', postArray)
      .then(function(response){
        self.setState({isVotesRegistered : true});
      })
      .catch(function(error){
        if (error.response) {
          if (error.response.status == 417) {
            var res = error.response.data.message.split(' ');
            errorMesages.push('Neteisingas biuletenių skaičius, daugiamandateje prabalsavo ' + res[7] + ', jūs užregistravote ' + res[10] + '.');
            self.setState({errorMesages : errorMesages});
          }
        }
      });
    }
  },
  render: function() {
    document.title='Atstovo Vienmandatės Rezultatai Vaizdas Rinkimai 2017';
    if (this.state.isLoading) {
      return <div><img src='/images/loading.gif'/></div>;
    }
    if (this.state.isVotesRegistered) {
      return(
        <div>
          {alerts.showSucces('Jūsų apylinkės balsai užregistruoti')}
        </div>
      );
    } else if (this.state.candidatesList.length == 0) {
      return(
        <div>
          {alerts.showSucces(this.state.districtInfo.countyName  + ' apygardai kandidatai dar nepriskirti.')}
        </div>
      );
    } {
      return (
        <SingleResultComponent
          list={this.state.candidatesList}
          registerVotes={this.registerVotes}
          onHandleSpoiledChange={this.onHandleSpoiledChange}
          onHandleSubmit={this.onHandleSubmit}
          errorMesages={this.state.errorMesages}
        />
      );
    }
  }
});


window.SingleResultContainer = SingleResultContainer;
