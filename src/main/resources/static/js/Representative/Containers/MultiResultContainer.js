var errorMesages = [];
var list = {};
var postArray =[];
var ratings = [];
function RatingnegativeValuesException(message) {
  this.message = message;
  this.name = 'RatingnegativeValuesException';
}
function getParties(self) {
  axios
    .get('/party')
    .then(function(response){
      self.setState({
        partyList :  response.data,
      });
    })
    .catch(function(err){
      console.error('MultiResultContainer.componentWillMount.axios.get.party', err);
    });
}
function getDistrict(self,id) {
  axios
    .get('/district/' + id)
    .then(function(response){
      self.setState({
        districtInfo :  response.data,
        isLoading : false,
      });
      if (response.data.resultMultiRegistered) {
        self.setState({isVotesRegistered : true});
      }
    })
    .catch(function(err){
      console.error('MultiResultContainer.componentWillMount.axios.get.district', err);
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
function getRatings(self,arr,pid) {
  var list = [];
  for (var i = 0; i < arr.length; i++) {
    if (arr[i].party == pid) {
      for (var j = 0; j < arr[i].members.length; j++) {
        if (arr[i].members[j].value != 0) {
          if (arr[i].members[j].value < 0) {
            errorMesages.push('Reitingo laukai negali turėti neigiamų reikšmių');
            self.setState({errorMesages : errorMesages});
            throw new RatingnegativeValuesException('Reitingo laukai negali turėti neigiamų reikšmių');}
          list.push(
            {
              candidate :
                {id : arr[i].members[j].key },
              points : arr[i].members[j].value,
            });
        }
      }
    }
  }
  return list;
}
var MultiResultContainer = React.createClass({
  getInitialState: function() {
    return {
      partyList: [],
      districtId : 5,
      districtInfo : [],
      isVotesRegistered : false,
      isLoading : true,
      errorMesages : [],
      ratings : [],
    };
  },
  componentWillMount: function() {
    getParties(this);
    getDistrict(this,this.state.districtId);
  },
  componentDidMount: function() {
    this.state.partyList.forEach(function(p) {
      this.state.ratings.push(p.id);
    }
    );
  },
  onChangePartyRating : function(pid,cid,points){
    if (ratings.length == 0) {
      this.state.partyList.forEach(function(p) {
        ratings.push(
          {
            party : p.id,
            members : p.members.map(function(m){return {key : m.id, value : 0};})
          }
        );});
    }

    for (var i = 0; i < ratings.length; i++) {
      if (ratings[i].party == pid) {
        for (var j = 0; j < ratings[i].members.length; j++) {
          if (ratings[i].members[j].key == cid) {
            ratings[i].members[j].value = points;
            break;
          }
        }
      }
    }
  },
  registerVotes : function(id,votes){
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
              'party' : {'id' : key},
              'district' : {'id' : this.state.districtId},
              'votes' : list[key],
              'datePublished' : new Date(),
              'rating' : getRatings(self,ratings,key),
            }
          );
        }
      }
      ratings = [];
      axios
      .post('/result-multi', postArray)
      .then(function(response){
        self.setState({isVotesRegistered : true});
      })
      .catch(function(error){
        if (error.response) {
          if (error.response.status == 417) {
            var res = error.response.data.message.split(' ');
            errorMesages.push('Neteisingas biuletenių skaičius, vienmandatieje prabalsavo ' + res[7] + ', jūs užregistravote ' + res[10] + '.');
            self.setState({errorMesages : errorMesages});
          }
        }
      });
    }
  },
  render: function() {
    if (this.state.isLoading) {
      return <div><img src='/images/loading.gif'/></div>;
    }
    if (this.state.isVotesRegistered) {
      return(
        <div>
          {alerts.showSucces('Jūsų apylinkės balsai užregistruoti')}
        </div>
      );
    } else {
      return (
        <MultiResultComponent
        list = {this.state.partyList}
        onNumberChange={this.onNumberChange}
        registerVotes={this.registerVotes}
        onHandleSubmit={this.onHandleSubmit}
        onHandleSpoiledChange={this.onHandleSpoiledChange}
        errorMesages={this.state.errorMesages}
        onChangePartyRating={this.onChangePartyRating}
        />
      );
    }
  }

});

window.MultiResultContainer = MultiResultContainer;
